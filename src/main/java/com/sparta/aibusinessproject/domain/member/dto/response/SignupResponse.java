package com.sparta.aibusinessproject.domain.member.dto.response;

import java.util.UUID;

public record SignupResponse(
        UUID id
) {
    public static SignupResponse from(UUID id) {
        return new SignupResponse(id);
    }
}