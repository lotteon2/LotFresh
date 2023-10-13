package com.bit.lotte.fresh.app.exception;


import com.bit.lotte.fresh.application.handler.ErrorDTO;
import com.bit.lotte.fresh.application.handler.GlobalExceptionHandler;
import com.bit.lotte.fresh.domain.exception.UserDomainException;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = {UserDomainException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(UserDomainException exception) {
    log.error(exception.getMessage(), exception);
    return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(exception.getMessage()).build();
  }

}