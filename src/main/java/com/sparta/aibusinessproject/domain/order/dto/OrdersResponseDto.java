package com.sparta.aibusinessproject.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrdersResponseDto {
    private UUID orderId;
    private String userId;
    private UUID storeId;
    private String status;
    private int totalPrice;
    private String detail;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
