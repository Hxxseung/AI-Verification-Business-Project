package com.sparta.aibusinessproject.domain.store.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreSearchListResponse {
    private UUID storeId;

    private UUID userId;

    private String name;

    private String category;

    private String address;

    private String description;

    private String phone;

    private String status;

    private Timestamp createdAt;

    private String createdBy;

    private Timestamp modifiedAt;

    private String modifiedBy;
}
