package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class LoginSessionExtendAuthDomainEvent extends
    AuthDomainEvent {

  public LoginSessionExtendAuthDomainEvent(AuthUserAbstract authUserAbstract,
      ZonedDateTime createdTime) {
    super(authUserAbstract, createdTime);
  }
}
