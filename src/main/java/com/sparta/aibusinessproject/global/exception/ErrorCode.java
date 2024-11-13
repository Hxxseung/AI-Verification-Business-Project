package com.sparta.aibusinessproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "스토어를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "패스워드가 잘못되었습니다."),
    DUPLICATED_STORENAME(HttpStatus.CONFLICT, "가게 이름이 중복됩니다."),
    DUPLICATED_CATEGORY(HttpStatus.CONFLICT, "카테고리 이름이 중복됩니다."),
    NOTFOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다."),
    DEFAULT_VALUE(HttpStatus.BAD_REQUEST, "기본 카테고리 종류이므로 변경이 불가능합니다.");

    private final HttpStatus status;
    private final String message;
}
