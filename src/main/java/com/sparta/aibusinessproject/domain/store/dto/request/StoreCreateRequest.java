package com.sparta.aibusinessproject.domain.store.dto.request;

import com.sparta.aibusinessproject.domain.store.dto.StoreData;

public record StoreCreateRequest(
        String name,
        String address,
        String description,
        String phone,
        String status,
        String createdBy,
        String modifiedBy,
        String deletedBy,
        String category
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
                .build();
    }
}