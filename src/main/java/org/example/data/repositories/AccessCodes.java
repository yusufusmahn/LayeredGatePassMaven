package org.example.data.repositories;

import org.example.data.models.AccessCode;
import org.example.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AccessCodes extends MongoRepository<AccessCode, String> {
    Optional<AccessCode> findByResidentAndCode(Resident resident, String accessToken);
    List<AccessCode> findByResident(Resident resident);
    Optional<AccessCode> findByEmailAndCode(String email, String accessToken);
    List<AccessCode> findByResidentEmail(String email);

}
