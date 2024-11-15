package com.sparta.aibusinessproject.domain.payment.controller;

import com.sparta.aibusinessproject.domain.payment.dto.PaymentRequestDto;
import com.sparta.aibusinessproject.domain.payment.dto.PaymentResponseDto;
import com.sparta.aibusinessproject.domain.payment.service.PaymentService;
import com.sparta.aibusinessproject.global.exception.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Response<PaymentResponseDto>> createPayment(@RequestBody @Valid PaymentRequestDto requestDto) {
        PaymentResponseDto response = paymentService.createPayment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(response));
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity<Response<PaymentResponseDto>> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @RequestParam @NotNull(message = "Status 값은 필수입니다.") String status) {
        PaymentResponseDto response = paymentService.updatePaymentStatus(paymentId, status);
        return ResponseEntity.ok(Response.success(response));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Response<PaymentResponseDto>> getPayment(@PathVariable UUID paymentId) {
        PaymentResponseDto response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(Response.success(response));
    }
}