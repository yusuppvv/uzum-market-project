package com.company.review;

import com.company.component.ApiResponse;
import com.company.review.DTO.ReviewsCr;
import com.company.review.DTO.ReviewResp;
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
    public ResponseEntity<ApiResponse<ReviewResp>> create(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.create(reviewsCr));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResp>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<ApiResponse<List<ReviewResp>>> findByProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findByProduct(id));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<ApiResponse<List<ReviewResp>>> findByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.findByUser(id));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ReviewResp>> update(@RequestBody ReviewsCr reviewsCr) {
        return ResponseEntity.ok(reviewService.update(reviewsCr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.delete(id));
    }
}
