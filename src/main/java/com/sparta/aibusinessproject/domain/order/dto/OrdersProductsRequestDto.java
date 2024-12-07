package com.sparta.aibusinessproject.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrdersProductsRequestDto {

    @NotNull(message = "Order ID must not be null") // Order ID 필수
    private UUID orderId;

    @NotNull(message = "Product ID must not be null") // Product ID 필수
    private UUID productId;

    @Min(value = 1, message = "Quantity must be at least 1") // 최소 수량 1 이상
    private int quantity;
}
