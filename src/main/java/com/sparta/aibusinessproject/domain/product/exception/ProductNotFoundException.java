package com.sparta.aibusinessproject.domain.product.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND); // ErrorCode에 정의된 메시지와 상태를 사용
    }
}
