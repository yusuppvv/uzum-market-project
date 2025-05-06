package com.company.product;

import com.company.component.ApiResponse;
import com.company.product.DTO.ProductCr;
import com.company.product.DTO.ProductResp;
import com.company.product.DTO.ProductUpdate;
import com.company.review.DTO.ReviewResp;
import com.company.review.DTO.ReviewsCr;
import com.company.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;


    @PostMapping("/create-review")
    public ResponseEntity<ApiResponse<ReviewResp>> ReviewCreate(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.create(reviewsCr));
    }


    @PutMapping("/update-review")
    public ResponseEntity<ApiResponse<ReviewResp>> update(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.update(reviewsCr));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ProductResp>> create(@RequestBody ProductCr productCr){
        return ResponseEntity.ok(productService.create(productCr));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResp>> getById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllByCategoryId(@PathVariable UUID categoryId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllByCategoryId(categoryId, page,size));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllBySellerId(@PathVariable UUID sellerId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllBySellerId(sellerId, page,size));
    }

    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<Page<ProductResp>>> getAllByPriceRange(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getAllByPriceRange(page ,size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResp>> update(@PathVariable UUID id, @RequestBody ProductUpdate productUpdate){
        return ResponseEntity.ok(productService.update(id, productUpdate));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        return ResponseEntity.ok(productService.delete(id));
    }



}
