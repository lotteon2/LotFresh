package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddAddressCommand {

  private AddressId addressId;
  @NotNull
  private Province province;
  @NotNull
  private String roadAddress;
  @NotNull
  private String detailAddress;
  @NotNull
  private String zipCode;
  @NotNull
  private final boolean defaultAddress =false;




}
