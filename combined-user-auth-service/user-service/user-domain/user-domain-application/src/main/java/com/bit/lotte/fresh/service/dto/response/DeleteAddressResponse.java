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
public class DeleteAddressResponse {

  @NotNull
  private final UserId userId;
  @NotNull
  private final AddressId addressId;
  @NotNull
  private  String message;

 public DeleteAddressResponse(User user, Address newDefaultAddress) {
    this.userId = user.getId();
    addressId = newDefaultAddress.getId();
    getMessage(newDefaultAddress);
  }

  private void getMessage(Address address){
      StringBuilder sb = new StringBuilder();
      sb.append(address.getDetailAddress()+"가 삭제되었습니다.");
      this.message =sb.toString();
  }
}
