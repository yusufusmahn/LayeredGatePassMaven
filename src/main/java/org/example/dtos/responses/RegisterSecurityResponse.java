package org.example.dtos.responses;

import lombok.Data;

@Data
public class RegisterSecurityResponse {
    private String id;
    private String name;
    private String email;
    private String role;
}