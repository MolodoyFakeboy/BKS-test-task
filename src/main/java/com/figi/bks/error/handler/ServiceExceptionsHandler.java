package com.figi.bks.error.handler;

import com.figi.bks.error.ExceptionResponse;
import com.figi.bks.error.exception.BadRequestException;
import com.figi.bks.error.exception.CustomExecutionException;
import com.figi.bks.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ServiceExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException ex) {
        return logAndReturnExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExecutionException.class)
    protected ResponseEntity<ExceptionResponse> handleExecutionException(RuntimeException ex) {
        return logAndReturnExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleNotFound(RuntimeException ex) {
        return logAndReturnExceptionResponse(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ExceptionResponse> logAndReturnExceptionResponse(Exception e, HttpStatus status) {
        log.error(e.getMessage());
        var response = ExceptionResponse.builder()
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, status);
    }
}
