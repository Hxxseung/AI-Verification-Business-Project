package com.sparta.aibusinessproject.domain.ai.dto;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AiData(
        String request,
        String response,
        UUID aiId,
        UUID productId
) {

public static Ai toEntity(AiData data) {
    return Ai.builder()
            .request(data.request())
            .response(data.response())
            .aiId(data.aiId())
            .build();
}

public static AiData from(Ai ai) {
    return AiData.builder()
            .request(ai.getRequest())
            .response(ai.getResponse())
            .aiId(ai.getAiId())
            .build();
}
}