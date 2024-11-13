package com.sparta.aibusinessproject.domain.order.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class OrderNotFoundException extends ApplicationException {

    public OrderNotFoundException(String message) {
        super(ErrorCode.ORDER_NOT_FOUND, message);
    }
}
