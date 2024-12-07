package com.sparta.aibusinessproject.domain.payment.service;

import com.sparta.aibusinessproject.domain.payment.dto.PaymentRequestDto;
import com.sparta.aibusinessproject.domain.payment.dto.PaymentResponseDto;
import com.sparta.aibusinessproject.domain.payment.entity.Payment;
import com.sparta.aibusinessproject.domain.payment.entity.PaymentStatus;
import com.sparta.aibusinessproject.domain.payment.repository.PaymentRepository;
import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 결제 생성
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        Payment payment = Payment.builder()
                .orderId(requestDto.getOrderId())
                .userId(requestDto.getUserId())
                .build();
        payment = paymentRepository.save(payment);
        return mapToResponseDto(payment);
    }

    // 결제 상태 업데이트
    public PaymentResponseDto updatePaymentStatus(UUID paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PAYMENT_NOT_FOUND));

        // PaymentStatus 검증 및 변환
        if (!PaymentStatus.isValid(status)) {
            throw new ApplicationException(ErrorCode.INVALID_PAYMENT_STATUS);
        }

        payment.updateStatus(PaymentStatus.from(status));  // 상태 업데이트
        payment = paymentRepository.save(payment);         // 저장

        return mapToResponseDto(payment);
    }

    // 특정 결제 조회
    public PaymentResponseDto getPaymentById(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PAYMENT_NOT_FOUND));
        return mapToResponseDto(payment);
    }

    // Payment 엔티티를 PaymentResponseDto로 매핑
    private PaymentResponseDto mapToResponseDto(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}