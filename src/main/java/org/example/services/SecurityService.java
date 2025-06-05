package org.example.services;


import org.example.dtos.requests.*;
import org.example.dtos.responses.*;

import java.util.List;

public interface SecurityService {
    RegisterSecurityResponse registerSecurity(RegisterSecurityRequest request);
    VerifyAccessCodeResponse verifyAccessCode(VerifyAccessCodeRequest request);
    LoginResponse login(LoginRequest request);
    List<UsedAccessCodeResponse> getAllUsedAccessCodes();
}
