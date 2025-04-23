package com.company.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryEntity categoryCreate(CategoryEntity category) {
        Optional<CategoryEntity> findByName = categoryRepository.findByName(category.getName());
        if (findByName.isPresent()) {
            throw new RuntimeException("Category with this name already exists");
        }
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<CategoryEntity> categoryUpdate(UUID id, CategoryEntity updated) {
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
