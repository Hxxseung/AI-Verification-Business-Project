package com.sparta.aibusinessproject.domain.store.dto;

import com.sparta.aibusinessproject.domain.store.entity.Store;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestDto {
    private UUID storeId;

    private UUID userId;

    private String name;

    private String category;

    private String address;

    private String description;

    private String phone;
}
