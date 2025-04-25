package com.company.product;

import com.company.delivery.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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


    public String uptadeProduct(UUID id, ProductEntity product) {
        ProductEntity existing =
    }

    public String deleteById(UUID id) {
        return null;
    }
}
