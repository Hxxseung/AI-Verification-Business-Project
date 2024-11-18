package com.sparta.aibusinessproject.domain.store.dto.request;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;

import java.util.UUID;

public record StoreCreateRequest(
        String name,
        String address,
        String description,
        String phone,
        String status,
        String createdBy,
        String modifiedBy,
        String deletedBy,
        String category,
        UUID userId,
        UUID storeId
) {

    // request -> dto
    public static StoreData toDto(StoreCreateRequest request) {
        return StoreData.builder()
                .name(request.name())
                .address(request.address())
                .description(request.description())
                .phone(request.phone())
                .status(request.status())
                .createdBy(request.createdBy())
                .modifiedBy(request.modifiedBy())
                .deletedBy(request.deletedBy())
                .userID(request.userId())
                .storeID(request.storeId())
                .build();
    }
}