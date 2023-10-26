package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class CreateAuthUserResponse {

  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private String message;

  public CreateAuthUserResponse(AuthUserId authUserId,
      AuthProvider authProvider, String message) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
    this.getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append(authProvider);
    sb.append("의 초기 기본 아이디가 생성되었습니다. 향후 회원가입을 완료시 정상적인 서비스 이용이 됩니다.");
    this.message = sb.toString();
  }
}


