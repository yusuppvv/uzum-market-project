package com.company.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository  extends JpaRepository<OrdersEntity, UUID> {
    Optional<OrdersEntity> findByIdAndVisibilityTrue(UUID id);

    Page<OrdersEntity> findAllByVisibilityTrue(Pageable pageable);

    Page<OrdersEntity> findAllByUserIdAndVisibilityTrue(UUID userId, Pageable pageable);
}
