package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class LoginAuthDomainEvent extends AuthDomainEvent {

  public LoginAuthDomainEvent(AuthUser authUser,
      ZonedDateTime createdTime) {
    super(authUser, createdTime);
  }
}
