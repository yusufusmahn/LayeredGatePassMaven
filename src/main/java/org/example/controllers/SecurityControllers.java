package org.example.controllers;

import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityControllers {

    @Autowired
    private SecurityService securityService;



    @PostMapping("/securities/register")
    public RegisterSecurityResponse registerSecurity(@RequestBody RegisterSecurityRequest request) {
        return securityService.registerSecurity(request);
    }

    @PostMapping("/securities/login")
    public LoginResponse loginSecurity(@RequestBody LoginRequest request) {
        return securityService.login(request);
    }


    @PostMapping("/access-codes/verify")
    public FindAccessCodeResponse verifyAccessCode(@RequestBody FindAccessCodeRequest request) {
        return securityService.verifyAccessCode(request);
    }

}
