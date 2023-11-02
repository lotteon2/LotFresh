package com.lotfresh.userservice.domain.member.service.response;

import com.lotfresh.userservice.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDetailResponse {
  private Long id;
  private String email;
  private String nickname;
  private Boolean isActive;

  @Builder
  public MemberDetailResponse(Long id, String email, String nickname, Boolean isActive) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.isActive = isActive;
  }

  public static MemberDetailResponse from(Member member) {
    return MemberDetailResponse.builder()
        .id(member.getId())
        .email(member.getEmail())
        .nickname(member.getNickname())
        .isActive(member.getIsActive())
        .build();
  }
}
