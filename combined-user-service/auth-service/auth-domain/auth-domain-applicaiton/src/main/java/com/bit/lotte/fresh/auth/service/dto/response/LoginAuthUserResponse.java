package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Getter;

@Getter
public class LoginAuthUserResponse {
  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private String message;

  public LoginAuthUserResponse(AuthUserId authUserId,
      AuthProvider authProvider) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
    this.getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append(authProvider);
    sb.append("의");
    sb.append("로그인이 완료되었습니다.");
    this.message = sb.toString();
  }
}
