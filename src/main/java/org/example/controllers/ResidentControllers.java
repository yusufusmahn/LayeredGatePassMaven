package org.example.controllers;

import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.GatePassException;
import org.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")
//@CrossOrigin("*")
public class ResidentControllers {

    @Autowired
    private ResidentService residentService;

    @PostMapping("/residents/register")
    public ResponseEntity<ApiResponse> registerResident(@RequestBody RegisterResidentRequest request) {
        try {
            RegisterResidentResponse response = residentService.registerResident(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/residents/login")
    public ResponseEntity<ApiResponse> loginResident(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = residentService.login(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/access-codes/generate")
    public ResponseEntity<ApiResponse> generateAccessCode(@RequestBody GenerateAccessCodeRequest request) {
        try {
            GenerateAccessCodeResponse response = residentService.generateAccessCode(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/access-codes/find")
    public ResponseEntity<ApiResponse> findAccessCode(@RequestBody FindAccessCodeRequest request) {
        try {
            FindAccessCodeResponse response = residentService.findAccessCode(request);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/residents/{residentId}/access-codes")
    public ResponseEntity<ApiResponse> getAccessCodes(@PathVariable("residentId") String residentId) {
        try {
            List<FindAccessCodeResponse> responseList = residentService.getAccessCodes(residentId);
            return new ResponseEntity<>(new ApiResponse(responseList, true), HttpStatus.OK);
        } catch (GatePassException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
}