package com.bit.lotte.fresh.application.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {
    private final String code;
    private final HttpStatus status;
    private final String message;
}
