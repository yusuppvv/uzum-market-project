package com.company.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    Optional<ReviewEntity> findByIdAndVisibilityTrue(UUID id);

    List<ReviewEntity> findAllByProductIdAndVisibilityTrue(UUID id);

    List<ReviewEntity> findAllByUserIdAndVisibilityTrue(UUID id);

    Optional<ReviewEntity> findByUserIdAndProductIdAndVisibilityTrue(UUID uuid, UUID uuid1);
}
