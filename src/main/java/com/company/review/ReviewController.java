package com.company.review;

import com.company.review.dto.ReviewCreation;
import com.company.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewResponse> create(@RequestBody ReviewCreation creation) {
        return ResponseEntity.ok(reviewService.create(creation));
    }

    @GetMapping("/get-all-productId/{productId}")
    public ResponseEntity<List<ReviewResponse>> getAllByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(reviewService.getAllByProductId(productId));
    }

    @GetMapping("/get-review/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PutMapping("/update/review/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID id, @RequestBody ReviewCreation reviewCreation) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewCreation));
    }

    @DeleteMapping("/delete-review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.deleteReviewById(id));
    }
}
