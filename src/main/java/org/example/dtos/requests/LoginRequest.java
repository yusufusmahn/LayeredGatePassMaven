package org.example.dtos.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
