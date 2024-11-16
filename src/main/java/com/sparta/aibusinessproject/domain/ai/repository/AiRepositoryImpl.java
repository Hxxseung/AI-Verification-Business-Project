package com.sparta.aibusinessproject.domain.ai.repository;

import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.ai.entity.Ai;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AiRepositoryImpl implements AiRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public Page<AiSearchListResponse> searchAi(Pageable pageable) {
        // CriteriaBuilder 생성
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // 결과를 위한 쿼리
        CriteriaQuery<Ai> query = cb.createQuery(Ai.class);
        Root<Ai> root = query.from(Ai.class);
        query.select(root);

        // 정렬 적용
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                if (order.getProperty().equals("createdAt")) {
                    if (order.isAscending()) {
                        query.orderBy(cb.asc(root.get("createdAt")));
                    } else {
                        query.orderBy(cb.desc(root.get("createdAt")));
                    }
                }
            });
        }

        // 쿼리 실행을 위한 TypedQuery 생성
        TypedQuery<Ai> typedQuery = entityManager.createQuery(query);

        // 전체 개수를 위한 카운트 쿼리
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Ai> countRoot = countQuery.from(Ai.class);
        countQuery.select(cb.count(countRoot));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        // 페이징 적용
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // 결과 조회 및 DTO 변환
        List<AiSearchListResponse> content = typedQuery.getResultList()
                .stream()
                .map(Ai::toResponseDto)
                .collect(Collectors.toList());

        // PageImpl 반환
        return new PageImpl<>(content, pageable, totalCount);
    }
}