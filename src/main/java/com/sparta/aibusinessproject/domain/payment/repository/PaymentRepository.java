package com.sparta.aibusinessproject.domain.payment.repository;

import com.sparta.aibusinessproject.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}