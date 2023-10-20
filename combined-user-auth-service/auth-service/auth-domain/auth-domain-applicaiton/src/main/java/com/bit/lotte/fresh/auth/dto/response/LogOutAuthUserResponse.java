package com.bit.lotte.fresh.auth.dto.response;


import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class LogOutAuthUserResponse {

  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private String message;
  public LogOutAuthUserResponse(AuthUserId authUserId,
      AuthProvider authProvider) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
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
