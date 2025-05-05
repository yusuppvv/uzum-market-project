package com.company.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  CartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByIdAndVisibilityTrue(UUID id);

    Page<CartEntity> findAllByVisibilityTrue(Pageable pageable);

    Optional<CartEntity> findByUserIdAndVisibilityTrue(UUID id);

    Page<CartEntity> findAllByUserIdAndVisibilityTrue(UUID id, Pageable pageable);

}
