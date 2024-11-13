package com.sparta.aibusinessproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_STORENAME(HttpStatus.CONFLICT, "가게 이름이 중복됩니다."),
    INVALID_STORE(HttpStatus.NOT_FOUND, "가게 정보가 없습니다."),
    INVALID_CATEGORY(HttpStatus.NOT_FOUND, "카테고리 정보가 없습니다. 다른 카테고리를 입력해 주세요"),
    DUPLICATED_CATEGORY(HttpStatus.CONFLICT, "카테고리 이름이 중복됩니다."),
    NOTFOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다."),
    DEFAULT_VALUE(HttpStatus.BAD_REQUEST, "기본 카테고리 종류 이므로 변경이 불가능합니다."),
    ALREADY_CATEGORY(HttpStatus.ALREADY_REPORTED, "이미 존재하는 카테고리 입니다");

    private final HttpStatus status;
    private final String message;
}

