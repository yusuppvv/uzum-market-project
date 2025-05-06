package com.company.category;

import com.company.category.DTO.CategoryCr;
import com.company.category.DTO.CategoryResp;
import com.company.component.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResp>> create(@RequestBody CategoryCr categoryCr) {
        return categoryService.create(categoryCr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResp>> getById(@PathVariable UUID id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return categoryService.getAll(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResp> update(@PathVariable UUID id, @RequestBody CategoryCr categoryCr) {
        return categoryService.update(id , categoryCr);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return categoryService.delete(id);
    }
}

