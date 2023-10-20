package com.bit.lotte.fresh.auth.dto.response;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class DeleteAuthUserResponse {

  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private String message;

  public DeleteAuthUserResponse(AuthUserId authUserId,
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
    sb.append("의 삭제가 완료되었습니다. 향후 서비스 이용시 다시 회원가입 부탁드립니다.");
    this.message = sb.toString();
  }
}
