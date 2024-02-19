package com.example.rsupport.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        ApiException ex = null;
        if (e instanceof ApiException) {
            ex = (ApiException) e;
        } else if (e instanceof MethodArgumentNotValidException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } else {
            ex = new ApiException(ResponseMessage.SERVER_EXCEPTION);
            log.error("", e);
        }
        return ResponseEntity
                .status(Objects.requireNonNull(ex).getStatus())
                .body(new ErrorResponse(ex));
    }
}
