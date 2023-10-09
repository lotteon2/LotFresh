package com.lotfresh.orderservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final Map<String,String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public void addValidation(FieldError fieldError) {
        String fieldName = fieldError.getField().substring(fieldError.getField().lastIndexOf('.')+1);
        validation.put(fieldName,fieldError.getDefaultMessage());
    }
}
