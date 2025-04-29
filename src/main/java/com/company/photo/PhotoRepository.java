package com.company.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<PhotoEntity, UUID> {
    Optional<PhotoEntity> findByIdAndVisibilityTrue(UUID id);
}
