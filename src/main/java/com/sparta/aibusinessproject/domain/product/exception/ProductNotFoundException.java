package com.sparta.aibusinessproject.domain.product.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException(String message) {
        super(ErrorCode.PRODUCT_NOT_FOUND, message);
    }
}
