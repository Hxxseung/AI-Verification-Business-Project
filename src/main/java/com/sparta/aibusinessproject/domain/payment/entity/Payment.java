package com.sparta.aibusinessproject.domain.payment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    @Builder
    public Payment(UUID orderId, UUID userId, PaymentStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status != null ? status : PaymentStatus.PENDING;
    }

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }
}
