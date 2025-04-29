package com.company.product;

import com.company.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByTitle(String title);

    Optional<ProductEntity> findByIdAndVisibilityTrue(UUID id);

    Page<ProductEntity> findAllByVisibilityTrue(Pageable pageable);

    Page<ProductEntity> findBySellerIdAndVisibilityTrue(UUID sellerId, Pageable pageable);

    Page<ProductEntity> findAllByTitleContainingIgnoreCaseAndVisibilityTrue(String title, Boolean visibility,
                                                                            Pageable pageable);

    Page<ProductEntity> findAllByPriceBetweenAndVisibilityTrue(double priceAfter, double priceBefore, Boolean visibility, Pageable pageable);

    Page<ProductEntity> findBySellerId(UUID sellerId, Pageable pageable);
}
