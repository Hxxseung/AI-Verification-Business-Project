package com.sparta.aibusinessproject.domain.order.service;

import com.sparta.aibusinessproject.domain.order.dto.OrdersRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersResponseDto;
import com.sparta.aibusinessproject.domain.order.entity.Orders;
import com.sparta.aibusinessproject.domain.order.exception.OrderNotFoundException;
import com.sparta.aibusinessproject.domain.order.repository.OrdersRepository;
import com.sparta.aibusinessproject.global.util.OrdersConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository; // OrdersRepository 주입

    public OrdersResponseDto createOrder(OrdersRequestDto requestDto) {
        // 주문 객체 생성 및 초기화
        Orders order = new Orders();
        order.setUserId(requestDto.getUserId());
        order.setStoreId(requestDto.getStoreId());
        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());

        // 주문 저장 및 결과 반환
        Orders savedOrder = ordersRepository.save(order);
        return OrdersConverter.convertToDto(savedOrder);
    }

    public String deleteOrder(UUID id) {
        // 주문 ID로 조회 및 삭제
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        ordersRepository.delete(order); // 주문 삭제
        return "Order with ID " + id + " successfully deleted.";
    }
}
