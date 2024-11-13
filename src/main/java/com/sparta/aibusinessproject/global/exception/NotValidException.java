package com.sparta.aibusinessproject.global.exception;

public class NotValidException extends ApplicationException {

    public NotValidException(String message) {
        super(ErrorCode.INVALID_REQUEST, message);
    }
}
