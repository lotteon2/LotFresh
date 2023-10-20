package com.bit.lotte.fresh.auth.exception;

import com.bit.lotte.fresh.user.common.exception.DomainException;

public class AuthUserDomainException  extends DomainException {

  public AuthUserDomainException(String message) {
    super(message);
  }

  public AuthUserDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
