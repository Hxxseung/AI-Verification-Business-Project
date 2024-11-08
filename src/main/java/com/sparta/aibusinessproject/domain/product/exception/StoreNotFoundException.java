package com.sparta.aibusinessproject.domain.product.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class StoreNotFoundException extends ApplicationException {

    public StoreNotFoundException() {
        super(ErrorCode.STORE_NOT_FOUND);
    }
}
