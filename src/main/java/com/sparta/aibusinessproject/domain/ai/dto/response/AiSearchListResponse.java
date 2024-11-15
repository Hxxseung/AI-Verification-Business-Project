package com.sparta.aibusinessproject.domain.ai.dto.response;

public record AiSearchListResponse(
        String userName,
        String question,
        String message
) {
}