package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerifyAccessCodeResponse {
    private String code;
    private String expiresAt;
    private boolean isUsed;
    private String visitorName;
    private String visitorEmail;
    private String visitorPhone;
    private String whomToSee;

}
