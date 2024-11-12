package com.sparta.aibusinessproject.global.util;

import com.sparta.aibusinessproject.domain.order.dto.OrdersProductsResponseDto;
import com.sparta.aibusinessproject.domain.order.entity.OrdersProducts;

public class OrdersProductsConverter {

    public static OrdersProductsResponseDto convertToDto(OrdersProducts ordersProducts) {
        // OrdersProducts 엔티티를 OrdersProductsResponseDto로 변환
        OrdersProductsResponseDto responseDto = new OrdersProductsResponseDto();
        responseDto.setOrderProductId(ordersProducts.getOrderProductId());
        responseDto.setOrderId(ordersProducts.getOrder().getOrderId());
        responseDto.setProductId(ordersProducts.getProduct().getProductId());
        responseDto.setQuantity(ordersProducts.getQuantity());
        responseDto.setCreatedAt(ordersProducts.getCreatedAt());
        responseDto.setModifiedAt(ordersProducts.getModifiedAt());
        return responseDto;
    }
}
