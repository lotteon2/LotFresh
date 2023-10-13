package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddUserAddressResponse {

  @NotNull
  private final UserId userId;
  @NotNull
  private AddressId addressId;
  @NotNull
  private String message;


  public AddUserAddressResponse(User user, Address address) {
    this.userId = user.getId();
    addressId = address.getId();
    this.message = this.getMessage();
  }

  private void getMessage(Address address) {
    StringBuilder sb = new StringBuilder();
    sb.append("권역: ").append(address.getProvince()).append("\n");
    sb.append("도로명 주소: ").append(address.getRoadAddress()).append("\n");
    sb.append("상세 주소: ").append(address.getDetailAddress()).append("\n");
    this.message = sb.toString();
  }
}