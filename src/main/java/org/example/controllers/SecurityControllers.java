package org.example.controllers;

import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.example.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    public ResponseEntity<ApiResponse> registerSecurity(@RequestBody RegisterSecurityRequest request) {
        try {
            RegisterSecurityResponse response = securityService.registerSecurity(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/securities/login")
    public ResponseEntity<ApiResponse> loginSecurity(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = securityService.login(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/access-codes/verify")
    public ResponseEntity<ApiResponse> verifyAccessCode(@RequestBody FindAccessCodeRequest request) {
        try {
            FindAccessCodeResponse response = securityService.verifyAccessCode(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
}


