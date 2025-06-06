package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsedAccessCodeResponse {
    private String code;
    private String expiresAt;
    private boolean used;
    private String whomToSee;
    private String visitorName;
    private String visitorEmail;
    private String visitorPhone;
}