package org.example.dtos.responses;

import lombok.Data;

@Data
public class RegisterResidentResponse {
    private String name;
    private String email;
    private String id;
}
