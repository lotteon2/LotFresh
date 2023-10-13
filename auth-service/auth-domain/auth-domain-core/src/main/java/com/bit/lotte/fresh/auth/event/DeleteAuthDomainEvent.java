package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class DeleteAuthDomainEvent extends AuthDomainEvent {

  public DeleteAuthDomainEvent(AuthUserAbstract authUserAbstract,
      ZonedDateTime createdTime) {
    super(authUserAbstract, createdTime);
  }
}
