package com.sparta.aibusinessproject.domain.store.dto.request;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;

public record StoreUpdateRequest(
        String name,
        String address,
        String description,
        String phone,
        String status,
        String modifiedBy
) {

    // request -> dto
    public static StoreData toDto(StoreUpdateRequest request) {
        return StoreData.builder()
                .name(request.name())
                .address(request.address())
                .description(request.description())
                .phone(request.phone())
                .status(request.status())
                .modifiedBy(request.modifiedBy())
                .build();
    }
}