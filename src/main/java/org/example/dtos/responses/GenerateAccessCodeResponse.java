package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateAccessCodeResponse {
    private String accessCode;
    private String residentId;
    private String expiresAt;
    private VisitorResponse visitor;
    private String whomToSee;

}
