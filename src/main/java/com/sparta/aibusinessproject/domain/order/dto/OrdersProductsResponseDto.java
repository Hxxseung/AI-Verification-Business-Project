package com.sparta.aibusinessproject.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrdersProductsResponseDto {
    private UUID orderProductId;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
