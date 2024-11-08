package com.sparta.aibusinessproject.domain.store.request;

public record StoreSearchListRequest(
        String category,
        String Address,
        int size
) {
}