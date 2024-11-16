package com.sparta.aibusinessproject.domain.ai.repository;

import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AiRepository extends JpaRepository<Ai, UUID>, AiRepositoryCustom {

    // member_Id가 일치하고 deletedAt이 NULL인 Ai만 조회
    List<Ai> findByMemberId(@Param("memberId") UUID memberId);

}
