package com.company.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServise {

    private final ProductRepository productRepository;

    public String createPorduct(ProductEntity product) {
        ProductEntity save = productRepository.save(product);
        return "zor aka ishladi";
    }

    public ResponseEntity<String> getAllProduct() {
     productRepository.findAll();
     return ResponseEntity.ok("zor aka ishladi");
    }


    public ResponseEntity<ProductEntity> uptadeProduct(UUID id, ProductEntity product) {
        ProductEntity existing = getById(id);
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        return ResponseEntity.ok(productRepository.save(existing));
    }

    private ProductEntity getById(UUID id) {
      return productRepository.findById(id).orElse(null);
    }

    public String deleteById(UUID id) {
        return deleteById(id);
    }
}
