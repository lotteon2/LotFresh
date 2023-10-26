package com.bit.lotte.fresh.auth.app.exception;

import com.bit.lotte.fresh.application.handler.ErrorDTO;
import com.bit.lotte.fresh.application.handler.GlobalExceptionHandler;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthUserGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {AuthUserDomainException.class})
  public ErrorDTO handleAuthDomainException(AuthUserDomainException exception) {
    log.error(exception.getMessage(), exception);
    return ErrorDTO.builder().code(HttpStatus.valueOf(400).getReasonPhrase())
        .status(HttpStatus.valueOf(400))
        .message(exception.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = {JwtException.class})
  public ErrorDTO handleException(AuthUserDomainException exception) {
    log.error(exception.getMessage(), exception);
    return ErrorDTO.builder().code(HttpStatus.valueOf(401).getReasonPhrase())
        .status(HttpStatus.valueOf(401))
        .message(exception.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = {BadCredentialsException.class})
  public ErrorDTO handleException(BadCredentialsException exception) {
    log.error(exception.getMessage(), exception);
    return ErrorDTO.builder().code(HttpStatus.valueOf(401).getReasonPhrase())
        .status(HttpStatus.valueOf(401))
        .message(exception.getMessage()).build();
  }


}

