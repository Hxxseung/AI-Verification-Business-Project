package com.sparta.aibusinessproject.domain.order.controller;

import com.sparta.aibusinessproject.domain.order.dto.OrdersProductsRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersProductsResponseDto;
import com.sparta.aibusinessproject.domain.order.service.OrdersProductsService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-products")
@RequiredArgsConstructor
public class OrdersProductsController {

    private final OrdersProductsService ordersProductsService; // OrdersProductsService 주입

    @PostMapping
    public Response<OrdersProductsResponseDto> createOrderProduct(@Valid @RequestBody OrdersProductsRequestDto requestDto) {
        // 주문-상품 생성 요청 처리
        OrdersProductsResponseDto responseDto = ordersProductsService.createOrderProduct(requestDto);
        return Response.success(responseDto); // 성공 메시지와 생성된 주문-상품 반환
    }
}
