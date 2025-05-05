package com.company.category;

import com.company.category.DTO.CategoryCr;
import com.company.category.DTO.CategoryResp;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<CategoryResp> create(CategoryCr categoryCr) {

        Optional<CategoryEntity> optionalCategory = categoryRepository
                .findByNameAndVisibilityTrue(categoryCr.getName());

        if (optionalCategory.isPresent()) {
            throw new AppBadRequestException("Category Already Exists!!!");
        }

        CategoryEntity saved = categoryRepository.save(CategoryEntity
                .builder()
                .name(categoryCr.getName())
                .build());

        return ResponseEntity.ok(
                toDTO(saved)
        );
    }

    public ResponseEntity<CategoryResp> getById(UUID id) {

        CategoryEntity categoryEntity = getCategoryEntityById(id);

        return ResponseEntity.ok(toDTO(categoryEntity));
    }

    public ResponseEntity<Page<CategoryResp>> getAll(int page, int size) {

//        Pageable pageable = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<CategoryResp> list = categoryRepository
                .findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
                new PageImpl<>(list, pageable, list.size())
        );

    }


    public ResponseEntity<CategoryResp> update(UUID id, CategoryCr categoryCr) {

        CategoryEntity categoryEntity = getCategoryEntityById(id);

        Optional<CategoryEntity> byNameAndVisibilityTrue = categoryRepository
                .findByNameAndVisibilityTrue(categoryCr.getName());

        if (byNameAndVisibilityTrue.isPresent()) {
            throw  new AppBadRequestException("Item Already Exisits!!!");
        }

        categoryEntity.setName(categoryCr.getName());

        CategoryEntity saved = categoryRepository.save(categoryEntity);

        return ResponseEntity.ok(toDTO(saved));
    }

    public ResponseEntity<String> delete(UUID id) {

        CategoryEntity categoryEntity = categoryRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);

        categoryEntity.setVisibility(false);


        categoryRepository.save(categoryEntity);

        return ResponseEntity.ok("Deleted");
    }

    private CategoryResp toDTO(CategoryEntity categoryEntity) {
        return CategoryResp
                .builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

    private CategoryEntity getCategoryEntityById(UUID id) {
        return categoryRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }
}
