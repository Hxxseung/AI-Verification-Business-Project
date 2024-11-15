package com.sparta.aibusinessproject.domain.ai.dto;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import lombok.Builder;

import java.util.UUID;

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