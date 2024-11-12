package com.sparta.aibusinessproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID storeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; // ENUM 타입으로 변경

    @Column(nullable = false)
    private int totalPrice;

    @Column(length = 255)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private Long deletedBy;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
