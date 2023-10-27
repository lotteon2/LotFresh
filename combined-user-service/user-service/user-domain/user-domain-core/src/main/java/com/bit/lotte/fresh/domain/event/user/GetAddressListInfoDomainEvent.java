package com.bit.lotte.fresh.domain.event.user;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.event.address.UserAddressDomainEvent;
import java.time.ZonedDateTime;

public class GetAddressListInfoDomainEvent extends
    UserAddressDomainEvent {

  public GetAddressListInfoDomainEvent(User user, Address address,
      ZonedDateTime createdTime) {
    super(user, address,createdTime);
  }
}
