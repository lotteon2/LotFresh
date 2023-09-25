package com.lotfresh.productservice.exception.advice;

import com.lotfresh.productservice.exception.CustomException;
import com.lotfresh.productservice.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> customException(CustomException e) {
    HttpStatus statusCode = e.getStatusCode();

    ErrorResponse body =
        ErrorResponse.builder()
            .code(statusCode)
            .message(e.getMessage())
            .validation(e.getValidation())
            .build();

    return ResponseEntity.status(statusCode).body(body);
  }
}
