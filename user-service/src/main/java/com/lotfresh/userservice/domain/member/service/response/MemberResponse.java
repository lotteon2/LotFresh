package com.lotfresh.userservice.domain.member.service.response;

import com.lotfresh.userservice.domain.address.entity.Address;
import com.lotfresh.userservice.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {
  private String email;
  private String nickname;
  private Boolean isActive;
  private String province;
  private String zipCode;
  private String roadAddress;
  private String detailAddress;

  @Builder
  private MemberResponse(
      String email,
      String nickname,
      Boolean isActive,
      String province,
      String zipCode,
      String roadAddress,
      String detailAddress) {
    this.email = email;
    this.nickname = nickname;
    this.isActive = isActive;
    this.province = province;
    this.zipCode = zipCode;
    this.roadAddress = roadAddress;
    this.detailAddress = detailAddress;
  }

  public static MemberResponse of(Member member, Address address) {
    return MemberResponse.builder()
        .email(member.getEmail())
        .nickname(member.getNickname())
        .isActive(member.getIsActive())
        .province(address != null ? address.getProvince() : null)
        .zipCode(address != null ? address.getZipCode() : null)
        .roadAddress(address != null ? address.getRoadAddress() : null)
        .detailAddress(address != null ? address.getRoadAddress() : null)
        .build();
  }
}
