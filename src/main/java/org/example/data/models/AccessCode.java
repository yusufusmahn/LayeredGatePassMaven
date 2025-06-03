package org.example.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("AccessCodes")
public class AccessCode {
    @Id
    private String id;
    private String code;
    @DBRef
    private Resident resident;
    private String email;
    private boolean isUsed;
    private LocalDateTime CreatedAt;
    private LocalDateTime expiresAt;
    private String WhomToSee;
    @DBRef
    private Visitor Visitor;


}
