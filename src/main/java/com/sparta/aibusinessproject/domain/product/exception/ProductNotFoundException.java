package com.sparta.aibusinessproject.domain.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message); // 예외 메시지를 부모 클래스로 전달
    }

    public ProductNotFoundException() {
        super("Product not found"); // 기본 메시지 제공
    }
}
