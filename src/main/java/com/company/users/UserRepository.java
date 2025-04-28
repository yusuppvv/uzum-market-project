package com.company.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByFullNameOrEmail(String fullName, String email);

    Page<UserEntity> findAllByVisibilityIsTrue(Pageable pageable);

    Optional<UserEntity> findByIdAndVisibilityIsTrue(UUID id);
}