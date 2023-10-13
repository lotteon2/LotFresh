package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;

public class UpdateAuthDomainRoleEvent extends AuthDomainEvent {

  public UpdateAuthDomainRoleEvent(AuthUserAbstract targetAuthUserAbstract,
      ZonedDateTime createdTime) {
    super(targetAuthUserAbstract, createdTime);
  }
}
