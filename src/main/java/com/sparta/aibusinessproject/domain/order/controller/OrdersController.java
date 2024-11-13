package com.sparta.aibusinessproject.domain.order.controller;

import com.sparta.aibusinessproject.domain.order.dto.OrdersRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.service.OrdersService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService; // OrdersService를 주입받아 사용

    @PostMapping
    public Response<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto requestDto) {
        // 주문 생성 요청 처리
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto);
        return Response.success(responseDto); // 성공 메시지와 생성된 주문 반환
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteOrder(@PathVariable UUID id) {
        // 주문 삭제 요청 처리
        String message = ordersService.deleteOrder(id);
        return Response.success(message); // 성공 메시지 반환
    }
}
