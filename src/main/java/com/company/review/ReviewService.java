package com.company.review;

import com.company.component.ApiResponse;
import com.company.component.Companents;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.review.dto.ReviewsCr;
import com.company.review.dto.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ApiResponse<ReviewResp> create(ReviewsCr reviewsCr) {

        Optional<ReviewEntity> optional = reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(reviewsCr.getUserId(), reviewsCr.getProductId());
        if (optional.isPresent()) {
            throw new BadRequestException("You already reviewed this product");
        }


        ReviewEntity reviewEntity = ReviewEntity
                .builder()
                .rating(reviewsCr.getRating())
                .comment(reviewsCr.getComment())
                .userId(reviewsCr.getUserId())
                .productId(reviewsCr.getProductId())
                .build();
        return new ApiResponse<>(toDto(reviewRepository.save(reviewEntity)));
    }



    public ApiResponse<ReviewResp> findById(UUID id) {
        return new ApiResponse<>(toDto(reviewRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new)));
    }

    public ApiResponse<List<ReviewResp>> findByProduct(UUID id) {
        return new ApiResponse<>(reviewRepository.findAllByProductIdAndVisibilityTrue(id).stream()
                .map(this::toDto)
                .toList());
    }

    public ApiResponse<List<ReviewResp>> findByUser(UUID id) {
        return new ApiResponse<>(reviewRepository.findAllByUserIdAndVisibilityTrue(id).stream()
                .map(this::toDto)
                .toList());
    }

    public ApiResponse<String> delete(UUID id) {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new);

        reviewEntity.setVisibility(false);

        reviewRepository.save(reviewEntity);

        return new ApiResponse<>(Companents.DELETED);
    }

    public ApiResponse<ReviewResp> update(ReviewsCr reviewsCr) {
        ReviewEntity reviewEntity = reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(reviewsCr.getUserId(), reviewsCr.getProductId()).orElseThrow(ItemNotFoundException::new);

        reviewEntity.setRating(reviewsCr.getRating());
        reviewEntity.setComment(reviewsCr.getComment());

        return new ApiResponse<>(toDto(reviewRepository.save(reviewEntity)));
    }

    private ReviewResp toDto(ReviewEntity reviewEntity) {
        return new ReviewResp(reviewEntity.getRating(), reviewEntity.getComment(), reviewEntity.getUserId(), reviewEntity.getProductId());
    }
}
