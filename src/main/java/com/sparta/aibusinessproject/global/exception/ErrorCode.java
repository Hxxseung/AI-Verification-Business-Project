package com.sparta.aibusinessproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorCode {

    private HttpStatus status;
    private String message;

}
