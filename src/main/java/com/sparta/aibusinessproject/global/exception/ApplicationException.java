package com.sparta.aibusinessproject.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode, String message) {
        super(message); // RuntimeException의 message 필드 초기화
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "errorCode=" + errorCode +
                ", message='" + getMessage() + '\'' + // getMessage()로 변경
                '}';
    }
}
