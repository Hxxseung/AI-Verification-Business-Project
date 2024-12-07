package com.sparta.aibusinessproject.domain.ai.repository;

import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiRepositoryCustom {
    Page<AiSearchListResponse> searchAi(Pageable pageable);
}