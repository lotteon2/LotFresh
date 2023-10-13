package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class CreateAuthDomainEvent extends AuthDomainEvent {

  public CreateAuthDomainEvent(AuthUserAbstract authUserAbstract,
      ZonedDateTime createdTime) {
    super(authUserAbstract, createdTime);
  }
}
