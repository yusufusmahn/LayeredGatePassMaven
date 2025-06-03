package org.example.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Residents")
public class Resident {
    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
}
