package com.sparta.aibusinessproject.domain.payment.entity;

public enum PaymentStatus {
    PENDING, SUCCESS, FAILED;

    // 상태 값이 유효한지 확인
    public static boolean isValid(String status) {
        if (status == null) {
            return false;
        }
        for (PaymentStatus paymentStatus : values()) {
            if (paymentStatus.name().equalsIgnoreCase(status.trim())) {
                return true;
            }
        }
        return false;
    }

    // 문자열을 PaymentStatus로 변환
    public static PaymentStatus from(String status) {
        for (PaymentStatus paymentStatus : values()) {
            if (paymentStatus.name().equalsIgnoreCase(status.trim())) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("잘못된 결제 상태: " + status);
    }
}
