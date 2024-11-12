package com.sparta.aibusinessproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //여기에 ErrorMessage 넣어서 쓰면 됩니다
    //ex) INVALID_STORE(HttpStatus.NOT_FOUND, "가게 정보가 없습니다.")
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found"),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "Store not found"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus status;
    private final String message;
}
