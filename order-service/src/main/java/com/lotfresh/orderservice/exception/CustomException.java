package com.lotfresh.orderservice.exception;

public class CustomException extends RuntimeException {
    ErrorCode errorCode;
    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
