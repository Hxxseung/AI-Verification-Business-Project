package com.sparta.aibusinessproject.global.exception;

import lombok.Getter;

@Getter
public class NotValidException extends RuntimeException {
    private ErrorCode errorCode;
    private final String message;

    public NotValidException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public NotValidException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotValidException{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}