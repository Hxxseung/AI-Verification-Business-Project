package com.sparta.aibusinessproject.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // todo: expiredException 처리 필요
        if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
            Authentication authentication = jwtUtil.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (jwt != null && jwtUtil.isTokenExpired(jwt)) {
            // Access Token 만료 시 Refresh Token 확인
            String refreshToken = getRefreshTokenFromCookies(request);

            if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)) {
                // Refresh Token이 유효하면 새로운 Access Token 발급
                String newAccessToken = jwtUtil.generateAccessTokenByRefreshToken(refreshToken);
                response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + newAccessToken);
            }
        }

        filterChain.doFilter(request, response);
    }



    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 쿠키에서 Refresh Token 가져오기
    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}