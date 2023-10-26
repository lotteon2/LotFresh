package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddAddressCommand {

  @NotNull
  private Province province;
  @NotNull
  private String roadAddress;
  @NotNull
  private String detailAddress;
  @NotNull
  private String zipCode;
  private final boolean defaultAddress =false;




}
