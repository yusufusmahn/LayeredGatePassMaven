package org.example.services;


import org.example.dtos.requests.*;
import org.example.dtos.responses.*;

public interface SecurityService {
    RegisterSecurityResponse registerSecurity(RegisterSecurityRequest request);
    FindAccessCodeResponse verifyAccessCode(FindAccessCodeRequest request);
    LoginResponse login(LoginRequest request);
}
