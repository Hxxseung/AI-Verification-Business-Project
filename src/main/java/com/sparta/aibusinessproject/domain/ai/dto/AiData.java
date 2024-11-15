package com.sparta.aibusinessproject.domain.ai.dto;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import com.sparta.aibusinessproject.domain.member.entity.Member;


public record AiData(
        Member member,
        String text,
        String aiResult
) {

    public static Ai AiData(Member member,String text,String aiResult){
        return Ai.builder()
                .member(member)
                .question(text)
                .message(aiResult)
                .build();
    }
}