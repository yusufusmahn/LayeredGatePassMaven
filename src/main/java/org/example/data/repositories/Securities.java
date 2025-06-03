package org.example.data.repositories;

import org.example.data.models.Security;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface Securities extends MongoRepository<Security, String> {
    Optional<Security> findByEmail(String email);
    boolean existsByEmail(String email);
}
