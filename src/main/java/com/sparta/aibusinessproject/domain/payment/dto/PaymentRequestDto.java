package com.sparta.aibusinessproject.domain.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequestDto {

    @NotNull(message = "Order ID는 필수 입력값입니다.")
    private UUID orderId;

    @NotNull(message = "User ID는 필수 입력값입니다.")
    private UUID userId;
}