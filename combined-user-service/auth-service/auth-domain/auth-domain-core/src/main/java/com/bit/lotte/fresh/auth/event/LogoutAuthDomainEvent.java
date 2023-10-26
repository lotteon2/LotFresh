package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class LogoutAuthDomainEvent extends
    LoginAuthDomainEvent {

  public LogoutAuthDomainEvent(AuthUser authUser,
      ZonedDateTime createdTime) {
    super(authUser, createdTime);
  }
}
