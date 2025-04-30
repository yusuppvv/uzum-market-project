package com.company.review;

import com.company.review.dto.ReviewCreation;
import com.company.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewEntity> create(@RequestBody ReviewEntity reviewEntity) {
        return ResponseEntity.ok(reviewService.create(reviewEntity));
    }

    @GetMapping("/get-all-productId")
    public ResponseEntity<String> getAllByProductId(@RequestParam("productId") UUID productId) {
        return ResponseEntity.ok(reviewService.getAllByProductId(productId));
    }

    @GetMapping("/get-review/{id}")
    public ResponseEntity<String> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PutMapping("/update/review/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID id, @RequestBody ReviewCreation reviewCreation) {
        return ResponseEntity.ok(reviewService.UpdateReview(id, reviewCreation));
    }

    @DeleteMapping("/delete-review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.ok().build();
    }
}
