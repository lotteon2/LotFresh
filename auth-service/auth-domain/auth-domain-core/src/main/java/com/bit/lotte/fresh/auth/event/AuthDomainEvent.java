package com.bit.lotte.fresh.auth.event;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import com.bit.lotte.fresh.user.common.event.DomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@AllArgsConstructor
@Getter
public abstract class AuthDomainEvent implements
    DomainEvent<AuthUserId> {

  private final AuthUser authUser;
  private final ZonedDateTime createdTime;


}
