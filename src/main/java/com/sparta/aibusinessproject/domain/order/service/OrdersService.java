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
        Orders order = new Orders();
        order.setUserId(requestDto.getUserId().toString());  // UUID를 String으로 변환
        order.setStoreId(requestDto.getStoreId());
        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());

        Orders savedOrder = ordersRepository.save(order);
        return OrdersConverter.convertToDto(savedOrder);
    }

    @Transactional
    public void softDeleteOrder(UUID id, String userId) {
        Orders order = ordersRepository.findById(id)
                .filter(o -> !o.isDeleted())
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You do not have permission to delete this order.");
        }

        order.setDeletedAt(LocalDateTime.now());
        order.setDeletedBy(userId);
        ordersRepository.save(order);
    }

    public OrdersResponseDto getOrderById(UUID id) {
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(OrderNotFoundException::new);
        return OrdersConverter.convertToDto(order);
    }

    public List<OrdersResponseDto> getAllOrders() {
        return ordersRepository.findAllByDeletedAtIsNull().stream()
                .map(OrdersConverter::convertToDto)
                .toList();
    }

    @Transactional
    public OrdersResponseDto updateOrder(UUID id, OrdersRequestDto requestDto) {
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(OrderNotFoundException::new);

        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());

        Orders updatedOrder = ordersRepository.save(order);
        return OrdersConverter.convertToDto(updatedOrder);
    }
}
