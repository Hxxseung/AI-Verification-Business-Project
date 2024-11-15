package com.sparta.aibusinessproject.global.util;

import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.entity.Orders;

public class OrdersConverter {

    public static OrdersResponseDto convertToDto(Orders order) {
        // Orders 엔티티를 OrdersResponseDto로 변환
        OrdersResponseDto responseDto = new OrdersResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setUserId(order.getUserId());
        responseDto.setStoreId(order.getStoreId());
        responseDto.setStatus(order.getStatus().name());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setDetail(order.getDetail());
        responseDto.setCreatedAt(order.getCreatedAt());
        responseDto.setModifiedAt(order.getModifiedAt());
        return responseDto;
    }
}
