package com.bit.lotte.fresh.auth.exception;

public class AuthorizationAuthDomainException extends
    AuthUserDomainException {

  public AuthorizationAuthDomainException(String message) {
    super(message);
  }

  public AuthorizationAuthDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
