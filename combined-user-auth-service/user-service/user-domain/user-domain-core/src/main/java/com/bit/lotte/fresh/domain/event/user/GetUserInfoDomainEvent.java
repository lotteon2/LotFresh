package com.bit.lotte.fresh.domain.event.user;

import com.bit.lotte.fresh.domain.entity.User;
import java.time.ZonedDateTime;

public class GetUserInfoDomainEvent extends UserDomainEvent {
  public GetUserInfoDomainEvent(User user,
      ZonedDateTime createdTime) {
    super(user, createdTime);
  }
}
