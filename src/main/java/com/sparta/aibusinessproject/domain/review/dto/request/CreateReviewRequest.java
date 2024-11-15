package com.sparta.aibusinessproject.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateReviewRequest (
        UUID orderId,
        @Size(max = 255, message = "리뷰는 255자를 초과할 수 없습니다")
        String contents,
        @Min(value = 1, message = "평점은 1점 이상이어야 합니다")
        @Max(value = 5, message = "평점은 5점 이하여야 합니다")
        Integer score
) {
}
