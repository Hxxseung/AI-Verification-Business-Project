package com.sparta.aibusinessproject.domain.store.response;

import com.sparta.aibusinessproject.domain.store.entity.Store;
import lombok.Builder;

@Builder
public record StoreSearchResponse(
        String name,
        String address,
        String description,
        String status
) {
    // entity -> searchDto  새로 생성하는 경우
    public static StoreSearchResponse from(Store store) {
        return StoreSearchResponse.builder()
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .status(store.getStatus())
                .build();
    }
}