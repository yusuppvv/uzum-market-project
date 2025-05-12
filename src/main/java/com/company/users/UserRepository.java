package com.company.users;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

   Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndVisibilityTrue(String email);
}
