package com.company.review;

import com.company.exception.AlreadyExistException;
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


    public ReviewResponse create(ReviewCreation reviewCreation) {
        Optional<ReviewEntity> byUserIdAndVisibilityTrue = reviewRepository.findByUserIdAndVisibilityTrue(reviewCreation.getUserId());
        if (byUserIdAndVisibilityTrue.isPresent()) {
            throw new AlreadyExistException("Review already exists for this user.");
        }
        else {
            ReviewEntity save = reviewRepository.save(new ReviewEntity(reviewCreation.getRating(), reviewCreation.getComment(), reviewCreation.getProductId(), reviewCreation.getUserId()));
            return new ReviewResponse(save.getRating(), save.getComment(), save.getProductId(), save.getUserId());
        }
    }

    public String getAllByProductId(UUID productId) {
        List<ReviewEntity> allByProductId = reviewRepository.getAllByProductId(productId);
        return allByProductId.stream()
                .map(ReviewEntity::getComment)
                .collect(Collectors.joining("\n"));

    }

}
