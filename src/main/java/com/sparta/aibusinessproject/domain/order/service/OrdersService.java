package com.sparta.aibusinessproject.domain.order.service;

import com.sparta.aibusinessproject.domain.order.dto.OrdersRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.entity.Orders;
import com.sparta.aibusinessproject.domain.order.exception.OrderNotFoundException;
import com.sparta.aibusinessproject.domain.order.repository.OrdersRepository;
import com.sparta.aibusinessproject.global.util.OrdersConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersResponseDto createOrder(OrdersRequestDto requestDto) {
        // 주문 생성 로직
        Orders order = new Orders();
        order.setUserId(requestDto.getUserId());
        order.setStoreId(requestDto.getStoreId());
        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());

        Orders savedOrder = ordersRepository.save(order);
        return OrdersConverter.convertToDto(savedOrder);
    }

    @Transactional
    public void softDeleteOrder(UUID id, Long userId) {
        // 주문 소프트 삭제
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        order.setDeletedAt(LocalDateTime.now());
        order.setDeletedBy(userId);
        ordersRepository.save(order); // 상태 업데이트
    }

    public OrdersResponseDto getOrderById(UUID id) {
        // 주문 단일 조회
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return OrdersConverter.convertToDto(order);
    }

    public List<OrdersResponseDto> getAllOrders() {
        // 소프트 삭제되지 않은 모든 주문 조회
        return ordersRepository.findAllByDeletedAtIsNull().stream()
                .map(OrdersConverter::convertToDto)
                .toList();
    }

    @Transactional
    public OrdersResponseDto updateOrder(UUID id, OrdersRequestDto requestDto) {
        // 주문 업데이트
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());
        Orders updatedOrder = ordersRepository.save(order);

        return OrdersConverter.convertToDto(updatedOrder);
    }
}
