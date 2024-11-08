package com.sparta.aibusinessproject.domain.member.dto.response;

import java.util.UUID;

public record LoginResponse(
        UUID id
) {
    public static LoginResponse from(UUID id) {
        return new LoginResponse(id);
    }
}
