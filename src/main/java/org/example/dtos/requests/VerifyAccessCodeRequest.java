package org.example.dtos.requests;

import lombok.Data;

@Data
public class VerifyAccessCodeRequest {
    private String accessCode;
}
