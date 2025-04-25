package com.company.product;

import lombok.RequiredArgsConstructor;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductServise productServise;

    @PostMapping
    public ResponseEntity<?> postProduct(ProductEntity product) {
        return ResponseEntity.ok(productServise.createPorduct(product));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getallProduct() {
        return productServise.getAllProduct();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> uptadeProduct(@PathVariable UUID id, @RequestBody ProductEntity product) {
        return ResponseEntity.ok(productServise.uptadeProduct(id ,product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(UUID id) {
        return ResponseEntity.ok(productServise.deleteById(id));
    }
}
