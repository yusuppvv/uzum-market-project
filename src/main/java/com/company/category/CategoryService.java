package com.company.category;

import com.company.category.DTO.CategoryCr;
import com.company.category.DTO.CategoryResp;
import com.company.component.ApiResponse;
import com.company.component.Companents;
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

    public ResponseEntity<ApiResponse<CategoryResp>> create(CategoryCr categoryCr) {

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
                new ApiResponse<>(toDTO(saved))
        );
    }

    public ResponseEntity<ApiResponse<CategoryResp>> getById(UUID id) {

        CategoryEntity categoryEntity = getCategoryEntityById(id);

        return ResponseEntity.ok(new ApiResponse<>(toDTO(categoryEntity)));
    }

    public ResponseEntity<ApiResponse<Page<CategoryResp>>> getAll(int page, int size) {

//        Pageable pageable = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Companents.CREATED_AT));

        List<CategoryResp> list = categoryRepository
                .findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
               new ApiResponse<>( new PageImpl<>(list, pageable, list.size()))
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

    public ResponseEntity<ApiResponse<String>> delete(UUID id) {

        CategoryEntity categoryEntity = categoryRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);

        categoryEntity.setVisibility(false);


        categoryRepository.save(categoryEntity);

        return ResponseEntity.ok(new ApiResponse<>(Companents.DELETED));
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
