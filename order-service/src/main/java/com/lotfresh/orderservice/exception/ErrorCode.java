package com.lotfresh.orderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND,"데이터가 존재하지 않습니다")



    ;

    private final HttpStatus status;
    private final String message;
}
