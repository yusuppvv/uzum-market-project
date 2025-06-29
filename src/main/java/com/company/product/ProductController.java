package com.company.product;

import com.company.component.ApiResponse;
import com.company.product.DTO.ProductCr;
import com.company.product.DTO.ProductResp;
import com.company.product.DTO.ProductUpdate;
import com.company.review.ReviewService;
import com.company.review.dto.ReviewResp;
import com.company.review.dto.ReviewsCr;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create-product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResp>> create(@RequestBody ProductCr productCr){
        return ResponseEntity.ok(productService.create(productCr));
    }

    @GetMapping("/get-by-id/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<ProductResp>> getById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/get-all")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @GetMapping("/category/{categoryId}")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllByCategoryId(@PathVariable UUID categoryId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllByCategoryId(categoryId, page,size));
    }

    @GetMapping("/seller/{sellerId}")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllBySellerId(@PathVariable UUID sellerId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllBySellerId(sellerId, page,size));
    }

    @GetMapping("/price-range")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllByPriceRange(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllByPriceRange(page ,size));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResp>> update(@PathVariable UUID id, @RequestBody ProductUpdate productUpdate){
        return ResponseEntity.ok(productService.update(id, productUpdate));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        return ResponseEntity.ok(productService.delete(id));
    }



}
