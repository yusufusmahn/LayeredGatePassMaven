package org.example.services;

import org.example.data.repositories.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SecurityServiceImplTest {

    @Autowired
    private Securities securities;

    @Autowired
    private AccessCodes accessCodes;

    @Autowired
    private Residents residents;

    @Autowired
    private Visitors visitors;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ResidentService residentService;

    private RegisterSecurityRequest registerRequest;
    private FindAccessCodeRequest findAccessCodeRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterSecurityRequest();
        registerRequest.setName("security");
        registerRequest.setEmail("security@gmail.com");
        registerRequest.setPassword("securepass");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("security@gmail.com");
        loginRequest.setPassword("securepass");

        findAccessCodeRequest = new FindAccessCodeRequest();
        findAccessCodeRequest.setCode("123456");

        securities.deleteAll();
        residents.deleteAll();
        visitors.deleteAll();
        accessCodes.deleteAll();

        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setName("resident");
        residentRequest.setEmail("resident@gmail.com");
        residentRequest.setPhone("1234567890");
        residentRequest.setAddress("address");
        residentRequest.setPassword("password");
        RegisterResidentResponse residentResponse = residentService.registerResident(residentRequest);

        GenerateAccessCodeRequest generateRequest = new GenerateAccessCodeRequest();
        generateRequest.setResidentId(residentResponse.getId());
        generateRequest.setDurationInHours(24);
        generateRequest.setVisitorName("bello");
        generateRequest.setVisitorEmail("bello@gmail.com");
        generateRequest.setVisitorPhone("0987654321");
        generateRequest.setWhomToSee("Security");
        GenerateAccessCodeResponse accessCodeResponse = residentService.generateAccessCode(generateRequest);

        findAccessCodeRequest.setResidentId(residentResponse.getId());
        findAccessCodeRequest.setCode(accessCodeResponse.getAccessCode());
    }

    @Test
    void testRegisterSecurity() {
        RegisterSecurityResponse response = securityService.registerSecurity(registerRequest);
        assertNotNull(response);
        assertEquals("Security", response.getName());
        assertEquals("security@gmail.com", response.getEmail());
    }

    @Test
    void testRegisterSecurity_AlreadyExists() {
        securityService.registerSecurity(registerRequest);
        assertThrows(EmailAlreadyExistsException.class, () -> securityService.registerSecurity(registerRequest));
    }


    @Test
    void testVerifyAccessCode_Valid() {
        FindAccessCodeResponse response = securityService.verifyAccessCode(findAccessCodeRequest);
        assertNotNull(response);
        assertTrue(response.isUsed());
        assertNotNull(response.getVisitor());
        assertEquals("Bello", response.getVisitor().getName());
    }

    @Test
    void testVerifyAccessCode_InvalidCode() {
        findAccessCodeRequest.setCode("invalidCode");
        assertThrows(InvalidAccessCodeException.class, () -> securityService.verifyAccessCode(findAccessCodeRequest));
    }

    @Test
    void testLoginSecurity() {
        securityService.registerSecurity(registerRequest);
        LoginResponse response = securityService.login(loginRequest);
        assertNotNull(response);
        assertEquals("security@gmail.com", response.getEmail());
    }

    @Test
    void testLoginSecurity_InvalidCredentials() {
        securityService.registerSecurity(registerRequest);
        loginRequest.setPassword("wrongpassword");
        assertThrows(InvalidLoginException.class, () -> securityService.login(loginRequest));
    }
}