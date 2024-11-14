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

    // 주문 생성
    public OrdersResponseDto createOrder(OrdersRequestDto requestDto) {
        Orders order = new Orders();
        order.setUserId(requestDto.getUserId());
        order.setStoreId(requestDto.getStoreId());
        order.setStatus(requestDto.getStatus());
        order.setTotalPrice(requestDto.getTotalPrice());
        order.setDetail(requestDto.getDetail());

        Orders savedOrder = ordersRepository.save(order);
        return OrdersConverter.convertToDto(savedOrder);
    }

    // 주문 소프트 삭제
    @Transactional
    public void softDeleteOrder(UUID id, Long userId) {
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(OrderNotFoundException::new); // 존재하지 않으면 예외 발생

        order.setDeletedAt(LocalDateTime.now()); // 삭제 시간 설정
        order.setDeletedBy(userId); // 삭제한 사용자 ID 설정
        ordersRepository.save(order);
    }

    // 주문 단일 조회
    public OrdersResponseDto getOrderById(UUID id) {
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(OrderNotFoundException::new); // 존재하지 않으면 예외 발생
        return OrdersConverter.convertToDto(order);
    }

    // 모든 주문 조회 (소프트 삭제되지 않은 주문만)
    public List<OrdersResponseDto> getAllOrders() {
        return ordersRepository.findAllByDeletedAtIsNull().stream()
                .map(OrdersConverter::convertToDto)
                .toList();
    }

    // 주문 업데이트
    @Transactional
    public OrdersResponseDto updateOrder(UUID id, OrdersRequestDto requestDto) {
        Orders order = ordersRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(OrderNotFoundException::new); // 존재하지 않으면 예외 발생

        order.setStatus(requestDto.getStatus()); // 상태 업데이트
        order.setTotalPrice(requestDto.getTotalPrice()); // 총 금액 업데이트
        order.setDetail(requestDto.getDetail()); // 상세 정보 업데이트
        Orders updatedOrder = ordersRepository.save(order);

        return OrdersConverter.convertToDto(updatedOrder);
    }
}
