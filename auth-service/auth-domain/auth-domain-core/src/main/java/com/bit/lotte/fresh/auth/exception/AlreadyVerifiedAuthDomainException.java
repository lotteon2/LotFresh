package com.bit.lotte.fresh.auth.exception;

public class AlreadyVerifiedAuthDomainException extends
    AuthUserDomainException {


  public AlreadyVerifiedAuthDomainException(String message) {
    super(message);
  }
}
