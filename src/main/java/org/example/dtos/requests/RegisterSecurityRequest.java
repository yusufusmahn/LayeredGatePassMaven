package org.example.dtos.requests;

import lombok.Data;

@Data
public class RegisterSecurityRequest {
    private String name;
    private String email;
//    private String phone;
    private String password;
}