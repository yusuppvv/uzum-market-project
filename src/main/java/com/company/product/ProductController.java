package com.company.product;

import com.company.product.dto.ProductCreationDto;
import com.company.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<ProductResponseDto>> getAllProduct(@RequestParam (defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProduct(page, size));
    }

    @GetMapping("/get-by-seller/{sellerId}")
    public ResponseEntity<Page<ProductResponseDto>> getBySellerId(@PathVariable UUID sellerId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getBySellerId(sellerId, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchByTitle(@RequestParam String title,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.searchByTitle(title, page, size));
    }

    @GetMapping("/get-by-range")
    public ResponseEntity<Page<ProductResponseDto>> getByRange(@RequestParam double minPrice,
                                                               @RequestParam double maxPrice,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getByRange(minPrice, maxPrice, page, size));
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
