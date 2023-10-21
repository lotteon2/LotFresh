package com.bit.lotte.fresh.auth.exception;

public class LoginFailedAuthDomainException extends
    AuthUserDomainException {

  public LoginFailedAuthDomainException(String message) {
    super(message);
  }

  public LoginFailedAuthDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
