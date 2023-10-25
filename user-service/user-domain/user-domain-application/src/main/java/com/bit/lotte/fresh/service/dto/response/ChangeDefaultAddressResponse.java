package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangeDefaultAddressResponse {
  @NotNull
 private final UserId userId;
  @NotNull
  private AddressId addressId;
  @NotNull
  private Province changedProvince;
  @NotNull
  private String message;


    public ChangeDefaultAddressResponse(User user, Address newDefaultAddress, Province province) {
    this.userId = user.getEntityId();
    addressId = newDefaultAddress.getEntityId();
    changedProvince = province;
    getMessage(newDefaultAddress);
  }

  private void getMessage(Address address){
      StringBuilder sb = new StringBuilder();
      sb.append("새로운 기본 주소는: ");
      sb.append("권역: ").append(address.getProvince());
      sb.append("도로명 주소: ").append(address.getRoadAddress());
      sb.append("상세 주소: ").append(address.getDetailAddress());
      sb.append("입니다.");
      this.message =sb.toString();
  }
}
