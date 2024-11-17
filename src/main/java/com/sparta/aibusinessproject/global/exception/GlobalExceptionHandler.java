package com.sparta.aibusinessproject.global.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response<String>> handleApplicationException(ApplicationException ex) {
        String errorMessage = ex.getErrorCode().name() + ": " + ex.getMessage();
        return new ResponseEntity<>(Response.error(errorMessage, errorMessage), ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = "INVALID_REQUEST: " + ex.getMessage();
        return ResponseEntity.badRequest().body(Response.error(errorMessage, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleGeneralException(Exception ex) {
        String errorMessage = "UNKNOWN_ERROR: " + ex.getMessage();
        return new ResponseEntity<>(Response.error(errorMessage, errorMessage), ErrorCode.INVALID_REQUEST.getStatus());
    }
}
