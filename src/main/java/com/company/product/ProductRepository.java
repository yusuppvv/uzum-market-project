package com.company.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByIdAndVisibilityTrue(UUID id);

    Page<ProductEntity> findAllByVisibilityTrue(Pageable pageable);

    Page<ProductEntity> findByCategoryIdAndVisibilityTrue(UUID categoryId, Pageable pageable);

    Page<ProductEntity> findBySellerIdAndVisibilityTrue(UUID sellerId, Pageable pageable);

    Page<ProductEntity> findAllByOrderByPriceAsc(Pageable pageable);

}
