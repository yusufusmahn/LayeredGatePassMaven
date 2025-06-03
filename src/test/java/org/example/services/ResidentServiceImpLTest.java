package org.example.services;

import org.example.data.repositories.*;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResidentServiceImpLTest {
    @Autowired
    private Residents residents;

    @Autowired
    private AccessCodes accessCodes;

    @Autowired
    private Visitors visitors;

    @Autowired
    private ResidentService residentService;

    private RegisterResidentRequest registerRequest;
    private GenerateAccessCodeRequest generateRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterResidentRequest();
        registerRequest.setName("yusuf usman");
        registerRequest.setEmail("YUSUF.USMAN@email.com");
        registerRequest.setPhone("1234567890");
        registerRequest.setAddress("abule oja");
        registerRequest.setPassword("password");

        generateRequest = new GenerateAccessCodeRequest();
        generateRequest.setResidentId("resident");
        generateRequest.setDurationInHours(24);
        generateRequest.setVisitorName("yours truly");
        generateRequest.setVisitorEmail("TRULY@gmail.com");
        generateRequest.setVisitorPhone("1234567890");
        generateRequest.setWhomToSee("anna");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("yusuf.usman@email.com");
        loginRequest.setPassword("password");

        residents.deleteAll();
        visitors.deleteAll();
        accessCodes.deleteAll();
    }

    @Test
    void testRegisterResident() {
        RegisterResidentResponse response = residentService.registerResident(registerRequest);
        assertNotNull(response);
        assertEquals("Yusuf Usman", response.getName());
        assertEquals("yusuf.usman@email.com", response.getEmail());
    }

    @Test
    void testRegisterResident_AlreadyExists() {
        residentService.registerResident(registerRequest);
        assertThrows(ResidentExistsException.class, () -> residentService.registerResident(registerRequest));
    }

    @Test
    void testGenerateAccessCode() {
        RegisterResidentResponse registerResponse = residentService.registerResident(registerRequest);
        generateRequest.setResidentId(registerResponse.getId());
        GenerateAccessCodeResponse response = residentService.generateAccessCode(generateRequest);
        assertNotNull(response);
        assertNotNull(response.getAccessCode());
        assertEquals(6, response.getAccessCode().length());
        assertEquals(registerResponse.getId(), response.getResidentId());
        assertNotNull(response.getVisitor());
        assertEquals("Yours Truly", response.getVisitor().getName());
        assertEquals("truly@gmail.com", response.getVisitor().getEmail());
        assertEquals("1234567890", response.getVisitor().getPhone());
    }

    @Test
    void testLoginResident() {
        residentService.registerResident(registerRequest);
        LoginResponse response = residentService.login(loginRequest);
        assertNotNull(response);
        assertEquals("yusuf.usman@email.com", response.getEmail());
        assertEquals("RESIDENT", response.getRole());
    }

    @Test
    void testLoginResident_InvalidCredentials() {
        residentService.registerResident(registerRequest);
        loginRequest.setPassword("wrongpassword");
        assertThrows(InvalidLoginException.class, () -> residentService.login(loginRequest));
    }

    @Test
    void testGetAccessCodes() {
        RegisterResidentResponse registerResponse = residentService.registerResident(registerRequest);
        String residentId = registerResponse.getId();

        generateRequest.setResidentId(residentId);
        residentService.generateAccessCode(generateRequest);

        generateRequest.setVisitorName("yusuf usman abdulsalam");
        generateRequest.setVisitorEmail("abdulsalam@email.com");
        residentService.generateAccessCode(generateRequest);

        List<FindAccessCodeResponse> accessCodes = residentService.getAccessCodes(residentId);
        assertNotNull(accessCodes);
        assertEquals(2, accessCodes.size());
        assertEquals("Yours Truly", accessCodes.get(0).getVisitor().getName());
        assertEquals("Yusuf Usman Abdulsalam", accessCodes.get(1).getVisitor().getName());
        assertEquals("abdulsalam@email.com", accessCodes.get(1).getVisitor().getEmail());
    }

    @Test
    void testGetAccessCodes_ResidentNotFound() {
        assertThrows(ResidentDoesNotExistException.class, () -> residentService.getAccessCodes("invalidResidentId"));
    }
}