package com.sparta.aibusinessproject.domain.payment.dto;

import com.sparta.aibusinessproject.domain.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PaymentResponseDto {
    private UUID paymentId;
    private UUID orderId;
    private UUID userId;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}
