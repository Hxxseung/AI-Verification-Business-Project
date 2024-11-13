package com.sparta.aibusinessproject.domain.member.service;

import com.sparta.aibusinessproject.domain.member.dto.request.LoginRequest;
import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import com.sparta.aibusinessproject.domain.member.dto.response.LoginResponse;
import com.sparta.aibusinessproject.domain.member.dto.response.MemberInfoResponse;
import com.sparta.aibusinessproject.domain.member.dto.response.SignupResponse;
import com.sparta.aibusinessproject.domain.member.entity.Member;
import com.sparta.aibusinessproject.domain.member.repository.MemberRepository;
import com.sparta.aibusinessproject.security.jwt.JwtUtil;
import com.sparta.aibusinessproject.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public SignupResponse signup(SignupRequest request) {
        memberRepository.checkDuplicatedUsername(request.username());
        memberRepository.checkDuplicatedEmail(request.email());
        Member member = memberRepository.save(Member.create(request, passwordEncoder));

        return SignupResponse.from(member.getId());
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtUtil.generateTokenDto(authentication);
        redisTemplate.opsForValue().set(request.username(), tokenDto);
        Member loginMember = memberRepository.getByUsername(request.username());

        return LoginResponse.of(loginMember.getId(), tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public MemberInfoResponse getMemberInfo(String username) {
        Member member = memberRepository.getByUsername(username);
        return MemberInfoResponse.from(member);
    }
}