package com.company.auth.verfication;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {
    void deleteByEmail(String email);


    Optional<VerificationEntity> findByEmailAndVisibilityTrue(String email);

    void removeByEmail(String email);
}
