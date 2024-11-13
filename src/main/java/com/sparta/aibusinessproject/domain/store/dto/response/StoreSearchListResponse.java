package com.sparta.aibusinessproject.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Builder;

@Builder
public record StoreSearchListResponse(
        String name,
        int minDeliveryPrice
        // TODO : Menu Entity 연동시 List형식으로 반환
//        List<MenuResponseDto> menuDto
){
}