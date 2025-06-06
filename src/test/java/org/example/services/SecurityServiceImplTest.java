package org.example.services;

import org.example.data.repositories.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.EmailAlreadyExistsException;
import org.example.exceptions.InvalidAccessCodeException;
import org.example.exceptions.InvalidLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    private VerifyAccessCodeRequest verifyAccessCodeRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        securities.deleteAll();
        residents.deleteAll();
        visitors.deleteAll();
        accessCodes.deleteAll();

        registerRequest = new RegisterSecurityRequest();
        registerRequest.setName("security");
        registerRequest.setEmail("security@gmail.com");
        registerRequest.setPassword("securepass");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("security@gmail.com");
        loginRequest.setPassword("securepass");

        verifyAccessCodeRequest = new VerifyAccessCodeRequest();
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

        verifyAccessCodeRequest.setAccessCode(accessCodeResponse.getAccessCode());
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
        VerifyAccessCodeResponse response = securityService.verifyAccessCode(verifyAccessCodeRequest);
        assertNotNull(response);
        assertTrue(response.isUsed());
        assertEquals("Bello", response.getVisitorName());
        assertEquals("bello@gmail.com", response.getVisitorEmail());
        assertEquals("0987654321", response.getVisitorPhone());
    }

    @Test
    void testVerifyAccessCode_InvalidCode() {
        verifyAccessCodeRequest.setAccessCode("invalidCode");
        assertThrows(InvalidAccessCodeException.class, () -> securityService.verifyAccessCode(verifyAccessCodeRequest));
    }

    @Test
    void testLoginSecurity() {
        securityService.registerSecurity(registerRequest);
        LoginResponse response = securityService.login(loginRequest);
        assertNotNull(response);
        assertEquals("security@gmail.com", response.getEmail());
        assertEquals("SECURITY", response.getRole());
    }

    @Test
    void testLoginSecurity_InvalidCredentials() {
        securityService.registerSecurity(registerRequest);
        loginRequest.setPassword("wrongpassword");
        assertThrows(InvalidLoginException.class, () -> securityService.login(loginRequest));
    }

    @Test
    void testGetAllUsedAccessCodes() {
        RegisterResidentRequest residentRequest = new RegisterResidentRequest();
        residentRequest.setName("resident");
        residentRequest.setEmail("resident123@gmail.com");
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

        VerifyAccessCodeRequest verifyRequest = new VerifyAccessCodeRequest();
        verifyRequest.setAccessCode(accessCodeResponse.getAccessCode());
        securityService.verifyAccessCode(verifyRequest);

        List<UsedAccessCodeResponse> response = securityService.getAllUsedAccessCodes();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        UsedAccessCodeResponse result = response.get(0);
        assertEquals(accessCodeResponse.getAccessCode(), result.getCode());
        assertTrue(result.isUsed());
        assertEquals("Bello", result.getVisitorName());
        assertEquals("bello@gmail.com", result.getVisitorEmail());
        assertEquals("0987654321", result.getVisitorPhone());
    }
}