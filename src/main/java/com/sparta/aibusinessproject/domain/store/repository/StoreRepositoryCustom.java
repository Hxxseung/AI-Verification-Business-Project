package com.sparta.aibusinessproject.domain.store.repository;

import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<StoreSearchListResponse> searchStores(StoreSearchListRequest searchDto, Pageable pageable);
}