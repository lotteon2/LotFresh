package com.lotfresh.orderservice.exception;

import lombok.Getter;

@Getter
public class SagaException extends RuntimeException{
    String message;
    String processName;

    public SagaException(String message){
        super(message);
        this.message = message;
    }
    public SagaException(String message, String processName) {
        super(message);
        this.message = message;
        this.processName = processName;
    }
}
