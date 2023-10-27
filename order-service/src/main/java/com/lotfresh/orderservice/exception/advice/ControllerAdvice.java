package com.lotfresh.orderservice.exception.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorResponse;
import com.lotfresh.orderservice.exception.SagaException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException e) {
        log.error("{}로부터 에러가 발생했습니다. 에러 메시지: {}",e.getStackTrace()[0], e.getErrorMessage());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 요청입니다");

        e.getFieldErrors().forEach(errorResponse::addValidation);
        errorResponse.getValidation().forEach((fieldName,errorMessage)
                -> log.error("사용자가 {}를 잘못 입력했습니다. {}",fieldName,errorMessage));

        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> feignException(FeignException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "서버에 문제가 발생해 요청이 실패했습니다");
        log.error("FeignClientError : {}요청을 보냈지만 실패했습니다. 에러 코드: {}",e.request(), e.status());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(SagaException.class)
    public ResponseEntity<ErrorResponse> sagaException(SagaException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "서버에 문제가 발생해 요청이 실패했습니다");
        log.error("SagaTransactionError : {}에서 트랜잭션이 실패했습니다. 에러 메시지: {}",e.getProcessName(), e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> jsonProcessingException(JsonProcessingException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "서버에 문제가 발생해 요청이 실패했습니다");
        log.error("JsonProcessingException");
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
