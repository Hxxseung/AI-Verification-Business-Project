package com.sparta.aibusinessproject.domain.order.dto;

import com.sparta.aibusinessproject.domain.order.entity.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrdersRequestDto {

    @NotNull(message = "User ID must not be null") // User ID 필수
    private UUID userId;

    @NotNull(message = "Store ID must not be null") // Store ID 필수
    private UUID storeId;

    @NotNull(message = "Status must not be null") // 주문 상태 필수
    private OrderStatus status;

    @Min(value = 1, message = "Total price must be at least 1") // 최소 주문 금액 검증
    private int totalPrice;

    private String detail; // 주문 세부사항
}
