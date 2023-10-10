package com.bit.lotte.fresh.domain.event.address;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@AllArgsConstructor
@Getter
public abstract class UserAddressDomainEvent {
  private final User user;
  private final Address address;
  private final ZonedDateTime createdTime;
}
