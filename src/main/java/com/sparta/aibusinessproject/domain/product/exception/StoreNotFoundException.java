package com.sparta.aibusinessproject.domain.product.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class StoreNotFoundException extends ApplicationException {

    public StoreNotFoundException(String message) {
        super(ErrorCode.STORE_NOT_FOUND, message); // super로 메시지와 에러코드 전달
    }
}
