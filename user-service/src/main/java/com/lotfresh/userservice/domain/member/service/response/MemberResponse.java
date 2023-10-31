package com.lotfresh.userservice.domain.member.service.response;

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

  @Builder
  private MemberResponse(String email, String nickname, Boolean isActive) {
    this.email = email;
    this.nickname = nickname;
    this.isActive = isActive;
  }

  public static MemberResponse from(Member member) {
    return MemberResponse.builder()
        .email(member.getEmail())
        .nickname(member.getNickname())
        .isActive(member.getIsActive())
        .build();
  }
}
