package com.bit.lotte.fresh.auth.service.dto.response;


import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class LogOutAuthUserResponse {

  private  AuthUserId authUserId;
  private  AuthProvider authProvider;
  private String message;
  public LogOutAuthUserResponse(AuthUserId authUserId,
      AuthProvider authProvider) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
    getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append(authProvider);
    sb.append("의 로그아웃이 완료되었습니다.");
    this.message = sb.toString();
  }
}