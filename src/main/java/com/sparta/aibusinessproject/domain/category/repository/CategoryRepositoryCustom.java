package com.sparta.aibusinessproject.domain.category.repository;

import com.sparta.aibusinessproject.domain.category.dto.response.CategoryListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryRepositoryCustom {
    Page<CategoryListResponse> searchCategories(Pageable pageable);
}