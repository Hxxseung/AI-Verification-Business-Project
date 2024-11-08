package com.sparta.aibusinessproject.domain.member.controller;

import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import com.sparta.aibusinessproject.domain.member.dto.response.LoginResponse;
import com.sparta.aibusinessproject.domain.member.entity.MemberRole;
import com.sparta.aibusinessproject.domain.member.service.MemberService;
import com.sparta.aibusinessproject.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@Valid @RequestBody SignupRequest request) {
        LoginResponse loginResponse = memberService.signup(request);
        return ResponseEntity.ok(loginResponse);
    }

}