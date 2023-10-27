package com.bit.lotte.fresh.auth.exception;

public class LoginTimeExpiredException extends
    AuthUserDomainException {

  public LoginTimeExpiredException(String message) {
    super(message);
  }

  public LoginTimeExpiredException(String message, Throwable cause) {
    super(message, cause);
  }
}
