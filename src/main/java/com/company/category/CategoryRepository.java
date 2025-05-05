package com.company.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity , UUID> {

    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByNameAndVisibilityTrue(String name);

    Page<CategoryEntity> findAllByVisibilityTrue(Pageable pageable);

    Optional<CategoryEntity> findByIdAndVisibilityTrue(UUID id);

//    @Query(nativeQuery = true, value = "select * from category where id = :id and ")
//    CategoryEntity findByIdAndNameAndVisibilityTrue(UUID id, String name);
}
