package com.company.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<String> CategoryCreate(@RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.CategoryCreate(category));
    }

    @GetMapping("/get-all")
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());

    }
    @PutMapping("/uptade/{id}")
    public ResponseEntity<CategoryEntity> update(
            @PathVariable UUID id,
            @RequestBody CategoryEntity updated) {
        return categoryService.categoryUptade(id,updated);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        categoryService.delete(id);
    }
}
