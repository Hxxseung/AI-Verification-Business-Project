package com.sparta.aibusinessproject.domain.ai.entity;

import com.sparta.aibusinessproject.domain.ai.dto.response.AiSearchListResponse;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "p_ai")
@Builder
@Where(clause = "deleted_at is NULL")
public class Ai {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(length = 50, nullable = false)
    private String question;

    private String message;

    @CreatedDate  // 생성 시간 자동 설정
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Builder 패턴에서 createdAt을 설정하는 메서드
    @Builder
    public Ai(Store store, String question, String message) {
        this.store = store;
        this.question = question;
        this.message = message;
    }

    public AiSearchListResponse toResponseDto() {
        return new AiSearchListResponse(
                store.getStoreId(),
                question,
                message
        );
    }

}