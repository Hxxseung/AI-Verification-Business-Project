package com.sparta.aibusinessproject.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String toString() {
        return "UserException{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}