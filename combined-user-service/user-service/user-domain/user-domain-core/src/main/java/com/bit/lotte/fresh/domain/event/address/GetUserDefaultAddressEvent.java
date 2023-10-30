package com.bit.lotte.fresh.domain.event.address;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GetUserDefaultAddressEvent extends UserAddressDomainEvent {

  public GetUserDefaultAddressEvent(User user,
      Address address, ZonedDateTime createdTime) {
    super(user, address, createdTime);
  }
}
