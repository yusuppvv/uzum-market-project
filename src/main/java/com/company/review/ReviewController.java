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
    public ResponseEntity<ReviewResponse> create(@RequestBody ReviewCreation reviewCreation) {
        return ResponseEntity.ok(reviewService.create(reviewCreation));
    }

    @GetMapping("get-all-productId")
    public ResponseEntity<String> getAllByProductId(@RequestParam("productId") UUID productId) {
        return ResponseEntity.ok(reviewService.getAllByProductId(productId));
    }
}
