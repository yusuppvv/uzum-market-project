package com.company.review;

import com.company.exception.AlreadyExistException;
import com.company.product.ProductEntity;
import com.company.product.ProductRepository;
import com.company.review.dto.ReviewCreation;
import com.company.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;


    public ReviewEntity create(ReviewEntity reviewEntity) {
        if (reviewRepository.findByProductIdAndUserId(reviewEntity.getProductId(), reviewEntity.getUserId()).isPresent())
            throw new RuntimeException("Bu foydalanuvchi allaqachon review qoldirgan");

        if (reviewEntity.getRating() < 1 || reviewEntity.getRating() > 5)
            throw new RuntimeException("Rating 1 dan 5 gacha bo‘lishi kerak");

        ReviewEntity saved = reviewRepository.save(reviewEntity);
        updateProductRating(reviewEntity.getProductId());
        return saved;
    }

    public String getAllByProductId(UUID productId) {
        List<ReviewEntity> allByProductId = reviewRepository.findAllByProductId(productId);
        return allByProductId.stream()
                .map(ReviewEntity::getComment)
                .collect(Collectors.joining("\n"));

    }

    public String getReviewById(UUID id) {
        Optional<ReviewEntity> byId = reviewRepository.findById(id);
         return byId.map(ReviewEntity::getComment).orElse("");
    }

    public ReviewResponse UpdateReview(UUID id, ReviewCreation reviewCreation) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElseThrow();
        reviewEntity.setRating(reviewCreation.getRating());
        reviewEntity.setComment(reviewCreation.getComment());
        reviewRepository.save(reviewEntity);
        return new ReviewResponse(reviewEntity.getRating(), reviewEntity.getComment(), reviewEntity.getProductId(), reviewEntity.getUserId());
    }

    public void deleteReviewById(UUID id) {
        reviewRepository.deleteById(id);
    }

    private void updateProductRating(UUID productId) {
        List<ReviewEntity> reviews = reviewRepository.findAllByProductId(productId);
        double avg = reviews.stream()
                .mapToInt(ReviewEntity::getRating)
                .average()
                .orElse(0.0);

        ProductEntity product = productRepository.findById(productId).orElseThrow();
        product.setRating(Math.round(avg * 10.0) / 10.0);
        productRepository.save(product);
    }
}
