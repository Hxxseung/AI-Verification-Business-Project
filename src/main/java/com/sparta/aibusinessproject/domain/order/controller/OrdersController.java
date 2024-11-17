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

    @PostMapping
    public Response<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto requestDto) {
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto);
        return Response.success(responseDto);
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteOrder(@PathVariable UUID id, Authentication authentication) {
        try {
            UUID userId = UUID.fromString(authentication.getName());
            ordersService.softDeleteOrder(id, userId);
            return Response.success("Order with ID " + id + " successfully soft deleted.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID string: " + authentication.getName(), e);
        }
    }

    @GetMapping("/{id}")
    public Response<OrdersResponseDto> getOrderById(@PathVariable UUID id) {
        OrdersResponseDto responseDto = ordersService.getOrderById(id);
        return Response.success(responseDto);
    }

    @GetMapping
    public Response<List<OrdersResponseDto>> getAllOrders() {
        List<OrdersResponseDto> responseDtoList = ordersService.getAllOrders();
        return Response.success(responseDtoList);
    }

    @PutMapping("/{id}")
    public Response<OrdersResponseDto> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody OrdersRequestDto requestDto) {
        OrdersResponseDto updatedOrder = ordersService.updateOrder(id, requestDto);
        return Response.success(updatedOrder);
    }
}
