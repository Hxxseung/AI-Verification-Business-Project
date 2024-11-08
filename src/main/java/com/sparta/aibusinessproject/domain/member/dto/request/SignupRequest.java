package com.sparta.aibusinessproject.domain.member.dto.request;

import com.sparta.aibusinessproject.domain.member.entity.MemberRole;

public record SignupRequest (
        String loginId,
        String name,
        String email,
        String phone,
        String address,
        String password,
        MemberRole role
) {

}
