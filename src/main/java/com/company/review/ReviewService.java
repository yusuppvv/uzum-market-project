package com.company.review;

import com.company.exception.AlreadyExistException;
import com.company.exception.ItemNotFoundException;
import com.company.exception.ReviewNotFoundException;
import com.company.review.dto.ReviewCreation;
import com.company.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public ReviewResponse create(ReviewCreation creation) {
        Optional<ReviewEntity> byUserIdAndVisibilityTrue = reviewRepository.findByUserIdAndVisibilityTrue(creation.getUserId());
        if (byUserIdAndVisibilityTrue.isPresent()) {
            throw new AlreadyExistException("User already has a review");
        }
        else {
            ReviewEntity save = reviewRepository.save(new ReviewEntity(creation.getRating(), creation.getComment(), creation.getProductId(), creation.getUserId()));
            return new ReviewResponse(save.getRating(), save.getComment(), save.getProductId(), save.getUserId());
        }
    }

    public List<ReviewResponse> getAllByProductId(UUID productId) {
        List<ReviewEntity> allByProductIdAndVisibilityTrue = reviewRepository.findAllByProductIdAndVisibilityTrue(productId);
        if (allByProductIdAndVisibilityTrue.isEmpty()) {
            throw new NullPointerException("There are no reviews for this product.");
        }
        else {
            return allByProductIdAndVisibilityTrue.stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    public ReviewResponse getReviewById(UUID id) {
        Optional<ReviewEntity> byIdAndVisibilityTrue = reviewRepository.findByIdAndVisibilityTrue(id);
        if (byIdAndVisibilityTrue.isPresent()) {
            return toDto(byIdAndVisibilityTrue.get());
        }
        else {
            throw new ReviewNotFoundException("No review by this Id");
        }
    }

    public ReviewResponse updateReview(UUID id, ReviewCreation reviewCreation) {
        Optional<ReviewEntity> byIdAndVisibilityTrue = reviewRepository.findByIdAndVisibilityTrue(id);
        if (byIdAndVisibilityTrue.isPresent()) {
            ReviewEntity reviewEntity = byIdAndVisibilityTrue.get();
            reviewEntity.setRating(reviewCreation.getRating());
            reviewEntity.setComment(reviewCreation.getComment());
            reviewEntity.setProductId(reviewCreation.getProductId());
            reviewEntity.setUserId(reviewCreation.getUserId());
            ReviewEntity save = reviewRepository.save(reviewEntity);
            return new ReviewResponse(save.getRating(), save.getComment(), save.getProductId(), save.getUserId());
        }
        else {
            throw new ItemNotFoundException();
        }
    }

    public String deleteReviewById(UUID id) {
        Optional<ReviewEntity> byIdAndVisibilityTrue = reviewRepository.findByIdAndVisibilityTrue(id);
        if (byIdAndVisibilityTrue.isPresent()) {
            ReviewEntity reviewEntity = byIdAndVisibilityTrue.get();
            reviewEntity.setVisibility(false);
            reviewRepository.save(reviewEntity);
            return "Successfully deleted";
        }
        else {
            throw new ItemNotFoundException();
        }
    }


    private ReviewResponse toDto(ReviewEntity reviewEntity) {
        return new ReviewResponse(reviewEntity.getRating(), reviewEntity.getComment(), reviewEntity.getProductId(), reviewEntity.getUserId());
    }
}
