package com.example.rsupport.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;

    public ApiException(ResponseMessage message) {
        this.status = message.getStatus();
        this.code = message.name();
        this.message = message.getMessage();
    }

    public ApiException(ResponseMessage message, String... msg) {
        this.status = message.getStatus();
        this.code = message.name();
        this.message = String.format(message.getMessage(), (Object[]) msg);
    }
}
