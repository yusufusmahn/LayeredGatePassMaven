package org.example.services;

import org.example.data.models.*;
import org.example.data.repositories.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.example.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentServiceImpL implements ResidentService {

    @Autowired
    private Residents residents;

    @Autowired
    private AccessCodes accessCodes;

    @Autowired
    private Visitors visitors;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public RegisterResidentResponse registerResident(RegisterResidentRequest request) {
        String email = Mapper.toLowerCaseEmail(request.getEmail());
        if (residents.existsByEmail(email)) {
            throw new ResidentExistsException("Email: " + email + " is already registered");
        }
        Resident resident = Mapper.mapToResident(request);
        residents.save(resident);
        return Mapper.mapToRegisterResidentResponse(resident);
    }

    @Override
    public GenerateAccessCodeResponse generateAccessCode(GenerateAccessCodeRequest request) {
        if (request.getWhomToSee() == null || request.getWhomToSee().trim().isEmpty()) {
            throw new IllegalArgumentException("whomToSee must be specified");
        }

        if (request.getResidentId() == null || request.getResidentId().trim().isEmpty()) {
            throw new IllegalArgumentException("residentId must be specified");
        }

        if (request.getDurationInHours() <= 0) {
            throw new IllegalArgumentException("durationInHours must be positive");
        }

        Resident resident = residents.findById(request.getResidentId())
                .orElseThrow(() -> new ResidentDoesNotExistException("Resident not found"));

        Visitor visitor = Mapper.mapToVisitor(request);
        Visitor savedVisitor = visitors.save(visitor);

//      String code = String.format("%06d", new Random().nextInt(999999));
        String code = generateAccessCode();
        AccessCode accessCode = Mapper.mapToAccessCode(request, code, resident, savedVisitor);
        accessCodes.save(accessCode);

        return Mapper.mapToGenerateAccessCodeResponse(accessCode);
    }

    @Override
    public FindAccessCodeResponse findAccessCode(FindAccessCodeRequest request) {
        Resident resident = residents.findById(request.getResidentId())
                .orElseThrow(() -> new ResidentDoesNotExistException("Resident not found"));
        AccessCode accessCode = accessCodes.findByResidentAndCode(resident, request.getCode())
                .orElseThrow(() -> new ResidentDoesNotExistException("Access code not found"));
        if (accessCode.getVisitor() == null) {
            throw new ResidentDoesNotExistException("Visitor not found");
        }
        return Mapper.mapToFindAccessCodeResponse(accessCode);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Resident resident = residents.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidLoginException("Invalid email or password"));
        if (!resident.getPassword().equals(request.getPassword())) {
            throw new InvalidLoginException("Invalid email or password");
        }
        return Mapper.mapToLoginResponse(resident);
    }

    @Override
    public List<FindAccessCodeResponse> getAccessCodes(String residentId) {
        Resident resident = residents.findById(residentId)
                .orElseThrow(() -> new ResidentDoesNotExistException("Resident not found"));
        List<AccessCode> accessCodesList = accessCodes.findByResident(resident);
        List<FindAccessCodeResponse> responses = new ArrayList<>();
        for (AccessCode accessCode : accessCodesList) {
            FindAccessCodeResponse response = Mapper.mapToFindAccessCodeResponse(accessCode);
            responses.add(response);
        }
        return responses;
    }

    private String generateAccessCode() {
        int code = secureRandom.nextInt(1000000);
        return String.format("%06d", code);
    }
}