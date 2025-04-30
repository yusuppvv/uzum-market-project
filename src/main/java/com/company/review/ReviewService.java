package com.company.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewEntity createReview(ReviewEntity review) {
        ReviewEntity save = reviewRepository.save(review);
        return save;
    }

    public String getAllReviews() {
        List<ReviewEntity> all = reviewRepository.findAll();
       return all.stream()
               .map(ReviewEntity::toString)
               .collect(Collectors.joining("\n"));
    }
}
