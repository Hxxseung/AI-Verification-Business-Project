package com.sparta.aibusinessproject.domain.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 상품 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 ID

    private String name; // 상품 이름
    private String description; // 상품 설명
    private double price; // 상품 가격
    private int stock; // 상품 재고

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // 상품 상태 (ENUM)

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 연결된 스토어

    private LocalDateTime createdAt; // 생성 일자
    private LocalDateTime updatedAt; // 수정 일자

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
