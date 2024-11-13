package com.sparta.aibusinessproject.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization"; // Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer "; // Token 식별자
    private final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 토큰 만료시간 30분
    private final long REFRESH_TOKEN_EXPIRE_TIME = 10000 * 60 * 1000L; // 토큰 만료시간 10000분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그"); // 로그 설정

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenDto generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = generateAccessToken(authentication.getName(), authorities);
        String refreshToken = generateRefreshToken(authentication.getName(), authorities);
        long now = (new Date()).getTime();

        return TokenDto.of(BEARER_PREFIX, accessToken, refreshToken, new Date(now + ACCESS_TOKEN_EXPIRE_TIME));
    }

    private String generateAccessToken(String username, String authorities) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    private String generateRefreshToken(String username, String authorities) {
        long now = (new Date()).getTime();
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, authorities)
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .claim("isRefreshToken", true) // refreshToken 임을 나타내는 클레임 추가
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // 토큰 검증
    public TokenStatus validateToken(String token) {
        // todo: ENUM 처리 필요
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return TokenStatus.VALID;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            return TokenStatus.EXPIRED;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return TokenStatus.INVALID;
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date()); // 현재 시간과 비교
        } catch (Exception e) {
            return true; // 토큰 파싱 실패 시 만료된 것으로 간주
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String generateAccessTokenByRefreshToken(String refreshToken) throws JsonProcessingException {
        // redis에서 refresh token 확인하고 refresh toekn 유효하면 access token 발급
        Claims refreshClaims = parseClaims(refreshToken);
        String username = refreshClaims.getSubject();

        // 2. Redis에서 사용자 정보 조회
        String userDataJson = (String) redisTemplate.opsForValue().get(username);
        if (userDataJson == null) {
            throw new IllegalArgumentException("No data found in Redis for user: " + username);
        }

        // 3. refresh token 검증
        TokenDto userToken = objectMapper.readValue(userDataJson, TokenDto.class);

        if (!refreshToken.equals(userToken.getRefreshToken()) || isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired Refresh Token");
        }

        // 5. 새로운 Access Token 생성
        String authorities = refreshClaims.get(AUTHORIZATION_KEY, String.class);
        String newAccessToken = generateAccessToken(username, authorities);
        Claims accessClaims = parseClaims(newAccessToken);
        TokenDto newUserToken = TokenDto.of(BEARER_PREFIX, newAccessToken, refreshToken, accessClaims.getExpiration());
        redisTemplate.opsForValue().set(username, newUserToken);

        return newAccessToken;
    }

}