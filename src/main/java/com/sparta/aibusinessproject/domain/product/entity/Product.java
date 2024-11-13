package com.sparta.aibusinessproject.domain.product.entity;

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
    private boolean isHidden; // 숨김 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status; // 상품 상태 (AVAILABLE, OUT_OF_STOCK, DISCONTINUED)

    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성 일자

    @Column
    private LocalDateTime updatedAt; // 수정 일자

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ProductStatus.AVAILABLE; // 기본 상태 설정
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
