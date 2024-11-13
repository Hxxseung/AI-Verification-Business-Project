package com.sparta.aibusinessproject.domain.member.dto.response;

import com.sparta.aibusinessproject.domain.member.entity.Member;
import java.util.UUID;

public record MemberInfoResponse(
        UUID id,
        String username,
        String email,
        String phone,
        String address,
        String role
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getRole().toString());
    }
}
