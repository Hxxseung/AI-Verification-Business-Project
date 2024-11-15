package com.sparta.aibusinessproject.domain.review.dto.response;

import com.sparta.aibusinessproject.domain.review.entity.Review;
import java.util.UUID;

public record DeleteReviewResponse (
        UUID id
) {
    public static DeleteReviewResponse from(Review review) {
        return new DeleteReviewResponse(review.getId());
    }
}
