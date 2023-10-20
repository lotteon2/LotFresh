package com.bit.lotte.fresh.domain.event.user;

import com.bit.lotte.fresh.domain.entity.User;
import java.time.ZonedDateTime;

public class DeleteUserDomainEvent extends
    UserDomainEvent {

  public DeleteUserDomainEvent(User user,
      ZonedDateTime createdTime) {
    super(user, createdTime);
  }
}
