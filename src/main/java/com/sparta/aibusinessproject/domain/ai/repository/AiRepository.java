package com.sparta.aibusinessproject.domain.ai.repository;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AiRepository extends JpaRepository<Ai, UUID>, AiRepositoryCustom {

    List<Ai> findByStore_StoreId(UUID storeId);
}
