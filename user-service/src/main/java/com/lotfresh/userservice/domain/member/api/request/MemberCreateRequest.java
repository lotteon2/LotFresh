package com.lotfresh.userservice.domain.member.api.request;

import com.lotfresh.userservice.domain.address.entity.Address;
import com.lotfresh.userservice.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberCreateRequest {
  private String province;
  private String roadAddress;
  private String addressDetail;
  private String zipCode;

  public Address toAddressEntity(Member member) {
    return Address.builder()
        .member(member)
        .province(province)
        .roadAddress(roadAddress)
        .detailAddress(addressDetail)
        .zipCode(zipCode)
        .build();
  }
}
