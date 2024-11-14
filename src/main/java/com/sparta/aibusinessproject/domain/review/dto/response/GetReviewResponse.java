package com.sparta.aibusinessproject.domain.review.dto.response;

import com.sparta.aibusinessproject.domain.review.entity.Review;
import java.util.UUID;

public record GetReviewResponse (
        UUID reviewId,
        UUID orderId,
        UUID memberId,
        String contents,
        Integer score
) {
    public static GetReviewResponse from(Review review) {
        return new GetReviewResponse(
                review.getId(),
                review.getOrder().getOrderId(),
                review.getOrder().getUserId(),
                review.getContents(),
                review.getScore());
    }
}
