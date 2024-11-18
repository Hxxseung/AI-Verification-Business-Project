package com.sparta.aibusinessproject.domain.order.controller;

import com.sparta.aibusinessproject.domain.order.dto.OrdersRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.service.OrdersService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // 주문 생성
    @PostMapping
    public Response<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto requestDto) {
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto);
        return Response.success(responseDto);
    }

    // 주문 소프트 삭제
    @DeleteMapping("/{id}")
    public Response<String> deleteOrder(@PathVariable UUID id, Authentication authentication) {
        String userId = authentication.getName(); // UUID 변환 없이 String 그대로 사용
        ordersService.softDeleteOrder(id, userId);
        return Response.success("Order with ID " + id + " successfully soft deleted.");
    }

    // 특정 주문 조회
    @GetMapping("/{id}")
    public Response<OrdersResponseDto> getOrderById(@PathVariable UUID id) {
        OrdersResponseDto responseDto = ordersService.getOrderById(id);
        return Response.success(responseDto);
    }

    // 모든 주문 조회
    @GetMapping
    public Response<List<OrdersResponseDto>> getAllOrders() {
        List<OrdersResponseDto> responseDtoList = ordersService.getAllOrders();
        return Response.success(responseDtoList);
    }

    // 주문 업데이트
    @PutMapping("/{id}")
    public Response<OrdersResponseDto> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody OrdersRequestDto requestDto) {
        OrdersResponseDto updatedOrder = ordersService.updateOrder(id, requestDto);
        return Response.success(updatedOrder);
    }
}
