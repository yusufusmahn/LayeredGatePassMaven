package org.example.services;


import org.example.data.models.*;
import org.example.data.repositories.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.example.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AccessCodes accessCodes;

    @Autowired
    private Securities securities;

    @Autowired
    private Residents residents;

    @Override
    public RegisterSecurityResponse registerSecurity(RegisterSecurityRequest request) {
        String email = Mapper.toLowerCaseEmail(request.getEmail());
        if (residents.existsByEmail(email) || securities.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email " + email + " is already registered");
        }

        Security security = Mapper.mapToSecurity(request);
        Security savedSecurity = securities.save(security);
        return Mapper.mapToRegisterSecurityResponse(savedSecurity);
    }


    @Override
    public VerifyAccessCodeResponse verifyAccessCode(VerifyAccessCodeRequest request) {
        String code = request.getAccessCode().trim();
        AccessCode accessCode = accessCodes.findByCode(code)
                .orElseThrow(() -> new InvalidAccessCodeException("Access code not found"));

        if (accessCode.isUsed()) {
            throw new InvalidAccessCodeException("Access code has already been used");
        }
        if (LocalDateTime.now().isAfter(accessCode.getExpiresAt())) {
            throw new InvalidAccessCodeException("Access code has expired");
        }

        accessCode.setUsed(true);
        accessCodes.save(accessCode);

        if (accessCode.getVisitor() == null) {
            throw new InvalidAccessCodeException("Visitor not found");
        }
        return Mapper.mapToVerifyAccessCodeResponse(accessCode);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Security security = securities.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidLoginException("Invalid email or password"));
        if (!security.getPassword().equals(request.getPassword())) {
            throw new InvalidLoginException("Invalid email or password");
        }
        return Mapper.mapToLoginResponse(security);
    }


    @Override
    public List<UsedAccessCodeResponse> getAllUsedAccessCodes() {
        List<AccessCode> allAccessCodes = accessCodes.findAll();
        List<UsedAccessCodeResponse> usedAccessCodes = new ArrayList<>();

        for (AccessCode accessCode : allAccessCodes) {
            if (accessCode.isUsed()) {
                UsedAccessCodeResponse response = Mapper.mapToUsedAccessCodeResponse(accessCode);
                usedAccessCodes.add(response);
            }
        }

        return usedAccessCodes;
    }


}