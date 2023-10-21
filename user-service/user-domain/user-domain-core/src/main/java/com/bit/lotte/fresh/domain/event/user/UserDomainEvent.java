package com.bit.lotte.fresh.domain.event.user;


import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.event.DomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public abstract class UserDomainEvent implements DomainEvent<UserId> {
  private final User user;
  private final ZonedDateTime createdTime;

}
