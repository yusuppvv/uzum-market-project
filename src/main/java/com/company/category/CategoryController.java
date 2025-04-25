package com.company.category;

import com.company.category.DTO.CategoryCr;
import com.company.category.DTO.CategoryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResp> create(@RequestBody CategoryCr categoryCr) {
        return categoryService.create(categoryCr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResp> getById(@PathVariable UUID id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResp>> getAll() {
        return categoryService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResp> update(@PathVariable UUID id, @RequestBody CategoryCr categoryCr) {
        return categoryService.update(id , categoryCr);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return categoryService.delete(id);
    }
}

