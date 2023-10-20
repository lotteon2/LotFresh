package com.bit.lotte.fresh.auth.app.exception;

import com.bit.lotte.fresh.application.handler.ErrorDTO;
import com.bit.lotte.fresh.application.handler.GlobalExceptionHandler;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthUserGlobalExceptionHandler extends GlobalExceptionHandler {

  @ExceptionHandler(value = {AuthUserDomainException.class})
  public ErrorDTO handleException(AuthUserDomainException exception) {
    log.error(exception.getMessage(), exception);
    return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(exception.getMessage()).build();
  }

}

