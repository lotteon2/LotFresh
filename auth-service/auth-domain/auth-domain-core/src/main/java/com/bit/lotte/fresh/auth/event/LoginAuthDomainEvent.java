package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class LoginAuthDomainEvent extends AuthDomainEvent {

  public LoginAuthDomainEvent(AuthUserAbstract authUserAbstract,
      ZonedDateTime createdTime) {
    super(authUserAbstract, createdTime);
  }
}
