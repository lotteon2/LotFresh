package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class CreateAuthDomainEvent extends AuthDomainEvent {

  public CreateAuthDomainEvent(AuthUser authUser,
      ZonedDateTime createdTime) {
    super(authUser, createdTime);
  }
}
