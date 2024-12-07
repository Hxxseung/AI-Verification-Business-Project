package com.sparta.aibusinessproject.domain.product.entity;

import com.sparta.aibusinessproject.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId; // 상품 UUID

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 연결된 Store

    @Column(nullable = false, length = 100)
    private String name; // 상품명

    @Column(length = 255)
    private String description; // 상품 설명

    @Column(nullable = false)
    @Positive(message = "가격은 0보다 커야 합니다.")
    private int price; // 상품 가격

    @Column(nullable = false)
    private boolean hidden; // 숨김 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status; // 상품 상태 (AVAILABLE, OUT_OF_STOCK, DISCONTINUED)

    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성 일자

    @Column
    private LocalDateTime updatedAt; // 수정 일자

    @Column
    private LocalDateTime deletedAt; // 소프트 삭제 필드

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now(); // 생성 시간 설정
        this.status = ProductStatus.AVAILABLE; // 기본 상태 설정
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // 수정 시간 설정
    }

    public boolean isDeleted() {
        return this.deletedAt != null; // 삭제 여부 확인
    }
}
