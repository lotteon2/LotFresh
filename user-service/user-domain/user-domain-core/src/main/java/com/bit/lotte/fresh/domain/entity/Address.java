package com.bit.lotte.fresh.domain.entity;

import com.bit.lotte.fresh.domain.exception.RegexValidationException;
import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.entity.BaseEntity;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Address extends BaseEntity<AddressId> {


  private Province province;
  private String roadAddress;
  private String detailAddress;
  private String zipCode;
  private boolean defaultAddress;



  public void validateZipCode(Address address){
    String zipCode = address.getZipCode();
    if (zipCode.matches("^\\d{5}$\n")) {
      throw new RegexValidationException("유효하지 않은 우편번호 형식입니다. 5자리 숫자를 넣어주세요.");
  }
  }
}
