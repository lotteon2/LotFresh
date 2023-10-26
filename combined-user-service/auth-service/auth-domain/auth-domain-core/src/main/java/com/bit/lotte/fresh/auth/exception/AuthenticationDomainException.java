package com.bit.lotte.fresh.auth.exception;


public class AuthenticationDomainException extends AuthUserDomainException {

  public AuthenticationDomainException(String message) {
    super(message);
  }

  public AuthenticationDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
