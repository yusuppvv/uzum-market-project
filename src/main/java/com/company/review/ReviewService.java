package com.company.review;

import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
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
        Optional<ReviewEntity> optional =
                reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(creation.getUserId(), creation.getProductId());
        if (optional.isPresent()) {
            throw new AppBadRequestException("Allaqchon Mavjud");
        }
            ReviewEntity save =
                    reviewRepository.save(toEntity(creation));

        return toDto(save);
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
            throw new ItemNotFoundException();
        }
    }

    public ReviewResponse updateReview(ReviewCreation reviewCreation) {

        Optional<ReviewEntity> optional =
                reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(reviewCreation.getUserId(), reviewCreation.getProductId());

        if (optional.isPresent()) {
            ReviewEntity reviewEntity = optional.get();
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

    private ReviewEntity toEntity(ReviewCreation creation) {
        return new ReviewEntity(creation.getRating(), creation.getComment(), creation.getProductId(), creation.getUserId());
    }
}
