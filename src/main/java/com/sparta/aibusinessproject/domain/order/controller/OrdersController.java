package com.sparta.aibusinessproject.domain.order.controller;

import com.sparta.aibusinessproject.domain.order.dto.OrdersRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.service.OrdersService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // 사용자 인증 객체
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public Response<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto requestDto) {
        // 주문 생성
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto);
        return Response.success(responseDto);
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteOrder(@PathVariable UUID id, Authentication authentication) {
        // 현재 로그인한 사용자 ID 추출
        Long userId = Long.valueOf(authentication.getName()); // 사용자 ID 추출 (클레임 기반 추출을 권장)

        // 주문 소프트 삭제
        ordersService.softDeleteOrder(id, userId);
        return Response.success("Order with ID " + id + " successfully soft deleted.");
    }
}
