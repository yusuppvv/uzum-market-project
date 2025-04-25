package com.company.product;

import com.company.product.dto.ProductCreationDto;
import com.company.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductCreationDto productCreationDto) {
        return ResponseEntity.ok(productService.createProduct(productCreationDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id,
                                                            @RequestBody ProductCreationDto product) {
        return ResponseEntity.ok(productService.updateProduct(id ,product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
