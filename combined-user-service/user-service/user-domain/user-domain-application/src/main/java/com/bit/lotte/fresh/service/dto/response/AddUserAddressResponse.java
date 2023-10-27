package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddUserAddressResponse {

  @NotNull
  private final UserId userId;
  @NotNull
  private String message;
  @NotNull
  private Address address;

  public AddUserAddressResponse(User user, Address address) {
    this.userId = user.getEntityId();
    this.address = address;
    getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append("새로운 주소가 생성돠었습니다.");
    sb.toString();
  }
}
