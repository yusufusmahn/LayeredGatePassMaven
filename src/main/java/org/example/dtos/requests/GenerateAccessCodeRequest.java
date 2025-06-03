package org.example.dtos.requests;

import lombok.Data;

@Data
public class GenerateAccessCodeRequest {
    private String residentId;
    private String email;
    private int durationInHours;
    private String visitorName;
    private String visitorEmail;
    private String visitorPhone;
    private String whomToSee;

}
