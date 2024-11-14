package com.sparta.aibusinessproject.domain.store.dto;

import com.sparta.aibusinessproject.domain.store.entity.Store;
import lombok.Builder;

import java.util.UUID;

@Builder
public record StoreData(
        String name,
        String address,
        String description,
        String phone,
        String status,
        String createdBy,
        String modifiedBy,
        String deletedBy,
        UUID userID


) {


    // dto -> entity
    public static Store toEntity(StoreData data) {
        return Store.builder()
                .name(data.name)
                .address(data.address)
                .description(data.description)
                .phone(data.phone)
                .status(data.status)
                .createdBy(data.createdBy)
                .modifiedBy(data.modifiedBy)
                .deletedBy(data.deletedBy)
                .userId(data.userID)
                .build();
    }

    // entity -> dto
    public static StoreData from(Store store) {
        return StoreData.builder()
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .phone(store.getPhone())
                .status(store.getStatus())
                .createdBy(store.getCreatedBy())
                .modifiedBy(store.getModifiedBy())
                .modifiedBy(store.getDeletedBy())
                .userID(store.getUserId())
                .build();
    }
    }