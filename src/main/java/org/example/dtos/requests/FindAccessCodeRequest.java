package org.example.dtos.requests;

import lombok.Data;

@Data
public class FindAccessCodeRequest {
    private String residentId;
    private String code;
    private String email;
}
