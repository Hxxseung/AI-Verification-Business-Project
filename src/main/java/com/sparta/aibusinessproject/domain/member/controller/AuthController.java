package com.sparta.aibusinessproject.domain.member.controller;

import com.sparta.aibusinessproject.domain.member.dto.request.LoginRequest;
import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import com.sparta.aibusinessproject.domain.member.dto.response.LoginResponse;
import com.sparta.aibusinessproject.domain.member.dto.response.SignupResponse;
import com.sparta.aibusinessproject.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse signupResponse = memberService.signup(request);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = memberService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

}