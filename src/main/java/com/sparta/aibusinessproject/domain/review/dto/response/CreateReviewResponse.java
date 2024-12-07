package com.sparta.aibusinessproject.domain.review.dto.response;

import java.util.UUID;

public record CreateReviewResponse(
        UUID reviewId
) {
    public static CreateReviewResponse from(UUID id) {
        return new CreateReviewResponse(id);
    }
}
