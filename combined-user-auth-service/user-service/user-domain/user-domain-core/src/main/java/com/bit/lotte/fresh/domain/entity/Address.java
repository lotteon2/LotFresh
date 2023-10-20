package com.bit.lotte.fresh.domain.entity;

import com.bit.lotte.fresh.domain.exception.RegexValidationException;
import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.entity.BaseEntity;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity<AddressId> {

  private Province province;
  private String roadAddress;
  private String detailAddress;
  private String zipCode;
  private boolean defaultAddress;
  private ZonedDateTime createdTime;
  private ZonedDateTime recentUpdatedTime;

  public Address(AddressId addressId, Province province, String roadAddress, String detailAddress,
      String zipCode, boolean defaultAddress, ZonedDateTime createdTime,
      ZonedDateTime recentUpdatedTime) {
    setId(addressId);
    this.province = province;
    this.roadAddress = roadAddress;
    this.detailAddress = detailAddress;
    this.zipCode = zipCode;
    this.defaultAddress = defaultAddress;
    this.createdTime = createdTime;
    this.recentUpdatedTime = recentUpdatedTime;
  }
  public void validateZipCode(Address address){
    String zipCode = address.getZipCode();
    if (zipCode.matches("^\\d{5}$\n")) {
      throw new RegexValidationException("유효하지 않은 우편번호 형식입니다. 5자리 숫자를 넣어주세요.");
  }
  }
}
