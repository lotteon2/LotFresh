package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.valueobject.Gender;
import java.util.List;
import javax.validation.constraints.NotNull;

public class UserDataResponse {

  @NotNull
  private final String name;
  @NotNull
  private final String contact;
  @NotNull
  private final List<Address> addressList;
  @NotNull
  private final Gender gender;

  public UserDataResponse(User user) {
    this.name = user.getName();
    this.contact = user.getContact();
    this.addressList = user.getAddressList();
    this.gender = user.getGender();
  }


}
