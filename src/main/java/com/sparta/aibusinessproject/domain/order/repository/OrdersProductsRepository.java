package com.sparta.aibusinessproject.domain.order.repository;

import com.sparta.aibusinessproject.domain.order.entity.OrdersProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, UUID> {
}
