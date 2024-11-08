package com.sparta.aibusinessproject.domain.member.service;

import com.sparta.aibusinessproject.domain.member.config.PasswordConfig;
import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import com.sparta.aibusinessproject.domain.member.dto.response.LoginResponse;
import com.sparta.aibusinessproject.domain.member.entity.Member;
import com.sparta.aibusinessproject.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse signup(SignupRequest request) {
        // todo: encode password dto에 저장
//        String password = passwordEncoder.encode(request.password());
        memberRepository.checkDuplicatedUsername(request.username());
        memberRepository.checkDuplicatedEmail(request.email());
        Member member = memberRepository.save(Member.create(request, passwordEncoder));

        return LoginResponse.from(member.getId());
    }
}