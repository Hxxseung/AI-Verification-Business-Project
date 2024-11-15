package com.sparta.aibusinessproject.domain.order.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class OrderNotFoundException extends ApplicationException {

    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND); // ErrorCode만 전달
    }
}
