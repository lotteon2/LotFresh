package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import java.time.ZonedDateTime;

public class UpdateUserAuthRoleDomainEvent extends AuthDomainEvent {

  public UpdateUserAuthRoleDomainEvent(AuthUser authUser, ZonedDateTime createdTime) {
    super(authUser, createdTime);
  }
}
