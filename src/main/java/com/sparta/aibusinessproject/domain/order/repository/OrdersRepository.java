package com.sparta.aibusinessproject.domain.order.repository;

import com.sparta.aibusinessproject.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {
}
