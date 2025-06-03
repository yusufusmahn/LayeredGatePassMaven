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
    public FindAccessCodeResponse verifyAccessCode(FindAccessCodeRequest request) {
        Resident resident = residents.findById(request.getResidentId())
                .orElseThrow(() -> new InvalidAccessCodeException("Resident not found"));
        AccessCode accessCode = accessCodes.findByResidentAndCode(resident, request.getCode())
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
        return Mapper.mapToFindAccessCodeResponse(accessCode);
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
}