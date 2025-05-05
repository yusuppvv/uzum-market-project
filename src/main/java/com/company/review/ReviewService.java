package com.company.review;

import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.review.DTO.ReviewsCr;
import com.company.review.DTO.ReviewResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewResp create(ReviewsCr reviewsCr) {

        Optional<ReviewEntity> optional = reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(reviewsCr.getUserId(), reviewsCr.getProductId());
        if (optional.isPresent()) {
            throw new BadRequestException("mazgi faqat birmarta qila olasan");
        }


        ReviewEntity reviewEntity = ReviewEntity
                .builder()
                .rating(reviewsCr.getRating())
                .comment(reviewsCr.getComment())
                .userId(reviewsCr.getUserId())
                .productId(reviewsCr.getProductId())
                .build();

        System.out.println(reviewsCr);

        return toDto(reviewRepository.save(reviewEntity));
    }



    public ReviewResp findById(UUID id) {
        return toDto(reviewRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new));
    }

    public List<ReviewResp> findByProduct(UUID id) {
        return reviewRepository.findAllByProductIdAndVisibilityTrue(id).stream()
                .map(this::toDto)
                .toList();
    }

    public List<ReviewResp> findByUser(UUID id) {
        return reviewRepository.findAllByUserIdAndVisibilityTrue(id).stream()
                .map(this::toDto)
                .toList();
    }

    public String delete(UUID id) {
        ReviewEntity reviewEntity = reviewRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new);

        reviewEntity.setVisibility(false);

        reviewRepository.save(reviewEntity);

        return "Deleted";
    }

    private ReviewResp toDto(ReviewEntity reviewEntity) {
        return new ReviewResp(reviewEntity.getRating(), reviewEntity.getComment(), reviewEntity.getUserId(), reviewEntity.getProductId());
    }

    public ReviewResp update(ReviewsCr reviewsCr) {
        ReviewEntity reviewEntity = reviewRepository.findByUserIdAndProductIdAndVisibilityTrue(reviewsCr.getUserId(), reviewsCr.getProductId()).orElseThrow(ItemNotFoundException::new);

        reviewEntity.setRating(reviewsCr.getRating());
        reviewEntity.setComment(reviewsCr.getComment());

        return toDto(reviewRepository.save(reviewEntity));
    }
}
