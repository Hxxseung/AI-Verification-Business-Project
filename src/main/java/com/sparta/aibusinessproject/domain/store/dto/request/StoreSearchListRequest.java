package com.sparta.aibusinessproject.domain.store.dto.request;

public record StoreSearchListRequest(
        String name,
        String category,
        String Address,
        int size
) {
}