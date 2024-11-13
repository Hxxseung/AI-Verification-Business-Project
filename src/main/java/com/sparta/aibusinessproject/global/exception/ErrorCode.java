package com.sparta.aibusinessproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.")
    //여기에 ErrorMessage 넣어서 쓰면 됩니다
    //ex) INVALID_STORE(HttpStatus.NOT_FOUND, "가게 정보가 없습니다.")
    ;
    private HttpStatus status;
    private String message;


}
