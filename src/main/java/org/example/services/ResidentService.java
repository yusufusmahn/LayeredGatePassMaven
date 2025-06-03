package org.example.services;


import org.example.dtos.requests.*;
import org.example.dtos.responses.*;

import java.util.List;

public interface ResidentService {
    RegisterResidentResponse registerResident(RegisterResidentRequest request);
    GenerateAccessCodeResponse generateAccessCode(GenerateAccessCodeRequest request);
    FindAccessCodeResponse findAccessCode(FindAccessCodeRequest request);
    LoginResponse login(LoginRequest request);
    List<FindAccessCodeResponse> getAccessCodes(String residentId);
}