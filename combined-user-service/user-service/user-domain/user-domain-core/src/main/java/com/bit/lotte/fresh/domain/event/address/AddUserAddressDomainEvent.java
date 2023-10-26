package com.bit.lotte.fresh.domain.event.address;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import java.time.ZonedDateTime;

public class AddUserAddressDomainEvent extends
    UserAddressDomainEvent {

  public AddUserAddressDomainEvent(User user, Address address,
      ZonedDateTime createdTime) {
    super(user,address, createdTime);
  }
}
