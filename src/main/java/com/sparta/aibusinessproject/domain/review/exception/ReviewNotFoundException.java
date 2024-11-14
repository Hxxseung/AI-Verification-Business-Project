package com.sparta.aibusinessproject.domain.review.exception;

import com.sparta.aibusinessproject.global.exception.ApplicationException;
import com.sparta.aibusinessproject.global.exception.ErrorCode;

public class ReviewNotFoundException extends ApplicationException {

    public ReviewNotFoundException() {
        super(ErrorCode.REVIEW_NOT_FOUND); // ErrorCode만 전달
    }
}
