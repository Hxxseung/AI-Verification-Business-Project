package com.sparta.aibusinessproject.domain.product.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String message) {
        super(message); // 예외 메시지를 부모 클래스에 전달
    }

    public StoreNotFoundException() {
        super("Store not found"); // 기본 메시지 제공
    }
}
