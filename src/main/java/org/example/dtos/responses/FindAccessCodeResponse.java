package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindAccessCodeResponse {
    private String residentId;
//    private String email;
    private String code;
    private String expiresAt;
    private boolean isUsed;
    private VisitorResponse visitor;
    private String whomToSee;

}
