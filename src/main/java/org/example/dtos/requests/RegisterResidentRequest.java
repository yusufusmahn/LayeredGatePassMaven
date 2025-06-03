package org.example.dtos.requests;

import lombok.Data;

@Data
public class RegisterResidentRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
}
