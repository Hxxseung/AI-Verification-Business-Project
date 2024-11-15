package com.sparta.aibusinessproject.domain.ai.repository;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import java.util.List;
import java.util.UUID;

@Repository
public interface AiRepository extends JpaRepository<Ai, UUID> , AiRepositoryCustom{

    List<Ai> findByUserName(String userName);
    List<Ai> findByUserUserId(String userName);

    @Modifying
    @Query("UPDATE Ai a SET a.deletedAt = CURRENT TIMESTAMP , a.deletedBy = :deletedBy WHERE a.id = :aiId")
    void delete(@Param("aiId") UUID aiId, @Param("deletedBy")String deletedBy);
}