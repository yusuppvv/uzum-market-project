package com.company.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public String CategoryCreate(CategoryEntity category) {
        return categoryRepository.save(category).toString();
    }

    public String getAllCategories() {
        return categoryRepository.findAll().toString();
    }

    public ResponseEntity<CategoryEntity> categoryUptade(UUID id, CategoryEntity updated) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            CategoryEntity categoryEntity = category.get();
            categoryEntity.setName(updated.getName());
            categoryRepository.save(categoryEntity);
            return new ResponseEntity<>(categoryEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}
