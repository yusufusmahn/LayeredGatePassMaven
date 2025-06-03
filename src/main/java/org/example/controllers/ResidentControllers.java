package org.example.controllers;

import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ResidentControllers {

    @Autowired
    private ResidentService residentService;

//    @Autowired
//    private SecurityService securityService;

    @PostMapping("/residents/register")
    public RegisterResidentResponse registerResident(@RequestBody RegisterResidentRequest request) {
        return residentService.registerResident(request);
    }


    @PostMapping("/access-codes/generate")
    public GenerateAccessCodeResponse generateAccessCode(@RequestBody GenerateAccessCodeRequest request) {
        return residentService.generateAccessCode(request);
    }

    @PostMapping("/access-codes/find")
    public FindAccessCodeResponse findAccessCode(@RequestBody FindAccessCodeRequest request) {
        return residentService.findAccessCode(request);
    }


    @GetMapping("/residents/{residentId}/access-codes")
    public List<FindAccessCodeResponse> getAccessCodes(@PathVariable("residentId") String residentId) {
        return residentService.getAccessCodes(residentId);
    }
}