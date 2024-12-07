package com.sparta.aibusinessproject.domain.order.repository;

import com.sparta.aibusinessproject.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {

    @Query("SELECT o FROM Orders o WHERE o.deletedAt IS NULL")
    List<Orders> findAllByDeletedAtIsNull();

    @Query("SELECT o FROM Orders o WHERE o.orderId = :id AND o.deletedAt IS NULL")
    Optional<Orders> findByIdAndDeletedAtIsNull(UUID id);

    default Orders getById(UUID id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
