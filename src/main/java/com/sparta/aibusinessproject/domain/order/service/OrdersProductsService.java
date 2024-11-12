package com.sparta.aibusinessproject.domain.order.service;

import com.sparta.aibusinessproject.domain.order.dto.OrdersProductsRequestDto;
import com.sparta.aibusinessproject.domain.order.dto.OrdersProductsResponseDto;
import com.sparta.aibusinessproject.domain.order.entity.OrdersProducts;
import com.sparta.aibusinessproject.domain.order.entity.Orders;
import com.sparta.aibusinessproject.domain.product.entity.Product;
import com.sparta.aibusinessproject.domain.order.repository.OrdersProductsRepository;
import com.sparta.aibusinessproject.domain.order.repository.OrdersRepository;
import com.sparta.aibusinessproject.domain.product.repository.ProductRepository;
import com.sparta.aibusinessproject.domain.product.exception.ProductNotFoundException;
import com.sparta.aibusinessproject.domain.order.exception.OrderNotFoundException;
import com.sparta.aibusinessproject.global.util.OrdersProductsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersProductsService {

    private final OrdersProductsRepository ordersProductsRepository; // 주입된 Repository들
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    public OrdersProductsResponseDto createOrderProduct(OrdersProductsRequestDto requestDto) {
        // Order 및 Product 유효성 검증 및 조회
        Orders order = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + requestDto.getOrderId()));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + requestDto.getProductId()));

        // OrdersProducts 객체 생성 및 저장
        OrdersProducts orderProduct = new OrdersProducts();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(requestDto.getQuantity());

        OrdersProducts savedOrderProduct = ordersProductsRepository.save(orderProduct);
        return OrdersProductsConverter.convertToDto(savedOrderProduct); // 결과 DTO 반환
    }
}
