package com.company.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByOrderIdAndVisibilityTrue(UUID orderId);

    List<CartEntity> findAllByVisibilityTrue();

    Optional<CartEntity> findByIdAndVisibilityTrue(UUID id);

    Optional<CartEntity> findByUserIdAndVisibilityTrue(UUID userId);
}
