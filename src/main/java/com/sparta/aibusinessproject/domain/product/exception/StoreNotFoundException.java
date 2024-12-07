package com.sparta.aibusinessproject.domain.product.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class StoreNotFoundException extends ApplicationException {

    // ErrorCode만 전달하도록 수정
    public StoreNotFoundException() {
        super(ErrorCode.STORE_NOT_FOUND);
    }
}
