package com.sparta.aibusinessproject.domain.member.dto.response;

import java.util.UUID;

public record LoginResponse (
        UUID id,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse of(UUID id, String accessToken, String refreshToken) {
        return new LoginResponse(id, accessToken, refreshToken);
    }
}
