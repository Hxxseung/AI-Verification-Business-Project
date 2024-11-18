package com.sparta.aibusinessproject.domain.ai.dto.response;

import java.util.UUID;

public record AiSearchListResponse(
        UUID storeID,
        String question,
        String message
) {
}