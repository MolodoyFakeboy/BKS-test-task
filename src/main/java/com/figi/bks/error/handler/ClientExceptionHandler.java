package com.figi.bks.error.handler;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException e) {
        String errorMessage = "Error: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
