package com.sparta.aibusinessproject.security.jwt;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date expiredTime;

    public static TokenDto of(String grantType, String accessToken, String refreshToken, Date expiredTime) {
        return TokenDto.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredTime(expiredTime)
                .build();
    }
}
