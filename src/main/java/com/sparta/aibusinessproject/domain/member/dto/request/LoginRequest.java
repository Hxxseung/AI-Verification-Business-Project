package com.sparta.aibusinessproject.domain.member.dto.request;

public record LoginRequest(
        String username,
        String password
) {
}
