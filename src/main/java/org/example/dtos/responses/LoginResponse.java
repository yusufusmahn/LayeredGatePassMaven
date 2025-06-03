package org.example.dtos.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String email;
    private String role;

}
