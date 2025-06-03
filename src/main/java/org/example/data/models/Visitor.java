package org.example.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Visitors")
public class Visitor {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
}
