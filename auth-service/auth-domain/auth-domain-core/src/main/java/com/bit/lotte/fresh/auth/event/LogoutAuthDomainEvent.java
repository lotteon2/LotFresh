package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class LogoutAuthDomainEvent extends
    LoginAuthDomainEvent {

  public LogoutAuthDomainEvent(AuthUserAbstract authUserAbstract,
      ZonedDateTime createdTime) {
    super(authUserAbstract, createdTime);
  }
}
