package com.sparta.aibusinessproject.domain.order.entity;

import com.sparta.aibusinessproject.domain.product.entity.Product;
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
public class OrdersProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order; // Orders 엔티티와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product; // Product 엔티티와 연관 관계

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
