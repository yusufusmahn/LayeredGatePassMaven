package org.example.data.repositories;

import org.example.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Residents extends MongoRepository<Resident, String> {
    Optional<Resident> findByEmail(String email);
    boolean existsByEmail(String email);
}
