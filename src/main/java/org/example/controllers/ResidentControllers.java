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
public class ResidentControllers {

    @Autowired
    private ResidentService residentService;


    @PostMapping("/residents/register")
    public ResponseEntity<ApiResponse> registerResident(@RequestBody RegisterResidentRequest request) {
        RegisterResidentResponse response = residentService.registerResident(request);
        try {
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (GatePassException e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/access-codes/generate")
    public ResponseEntity<ApiResponse> generateAccessCode(@RequestBody GenerateAccessCodeRequest request) {
        GenerateAccessCodeResponse response = residentService.generateAccessCode(request);
        try {
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch(GatePassException e){
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/access-codes/find")
    public ResponseEntity<ApiResponse> findAccessCode(@RequestBody FindAccessCodeRequest request) {
        FindAccessCodeResponse response = residentService.findAccessCode(request);
        try{
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch(GatePassException e){
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/residents/{residentId}/access-codes")
    public ResponseEntity<ApiResponse>  getAccessCodes(@PathVariable("residentId") String residentId) {
        List<FindAccessCodeResponse> responseList = residentService.getAccessCodes(residentId);
        try{
            return new ResponseEntity<>(new ApiResponse(responseList, true), HttpStatus.OK);
        }catch(GatePassException e){
            return new ResponseEntity<>(new ApiResponse(responseList, false), HttpStatus.BAD_REQUEST);
        }
    }
}