package com.sparta.aibusinessproject.domain.category.dto.response;

import com.sparta.aibusinessproject.domain.category.entity.Category;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CategoryListResponse(
        UUID categoryId,
        String name
) {

    public static CategoryListResponse from(Category category) {
        return CategoryListResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }
}