package com.company.review;

import com.company.component.ApiResponse;
import com.company.review.dto.ReviewsCr;
import com.company.review.dto.ReviewResp;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewService reviewService;

    @PostMapping
    @PermitAll
    public ResponseEntity<ApiResponse<ReviewResp>> create(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.create(reviewsCr));
    }


    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<ReviewResp>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping("/get-product/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<List<ReviewResp>>> findByProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findByProduct(id));
    }

    @GetMapping("/get-user/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<List<ReviewResp>>> findByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findByUser(id));
    }

    @PutMapping
    @PermitAll
    public ResponseEntity<ApiResponse<ReviewResp>> update(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.update(reviewsCr));
    }

    @DeleteMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.delete(id));
    }
}
