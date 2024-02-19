package com.example.rsupport.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(ApiException ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

    public ErrorResponse(String message) {
        this.code = ResponseMessage.SERVER_EXCEPTION.name();
        this.message = message;
    }
}
