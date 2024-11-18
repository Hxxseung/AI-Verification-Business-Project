package com.sparta.aibusinessproject.domain.ai.dto;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import com.sparta.aibusinessproject.domain.store.entity.Store;


public record AiData(
        Store store,
        String text,
        String aiResult
) {

    public static Ai AiData(Store store,String text,String aiResult){
        return Ai.builder()
                .store(store)
                .question(text)
                .message(aiResult)
                .build();
    }
}