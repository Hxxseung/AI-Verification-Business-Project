package com.sparta.aibusinessproject.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(NotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_REQUEST.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_REQUEST.getStatus());
    }
}
