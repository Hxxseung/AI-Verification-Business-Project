package com.sparta.aibusinessproject.domain.member.dto.request;

import com.sparta.aibusinessproject.domain.member.entity.MemberRole;
import jakarta.validation.constraints.Pattern;

public record SignupRequest (
        @Pattern(regexp = "^[a-z0-9]{4,10}$",
                message = "아이디는 영문 소문자 및 숫자 4~10자리여야 합니다.")
        String username,
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,15}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!@#$%^&*)를 허용하고 8~15자리여야 합니다.")
        String password,
        String nickname,
        String email,
        String phone,
        String address,
        MemberRole role
) {

}
