package com.bit.lotte.fresh.auth.dto.response;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class UpdateLoginSessionTimeResponse {

  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private final ZonedDateTime zonedDateTime;
  private String message;

  public UpdateLoginSessionTimeResponse(
      AuthUserId authUserId, AuthProvider authProvider, ZonedDateTime zonedDateTime,
      String message) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
    this.zonedDateTime = zonedDateTime;
    getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append(authProvider);
    sb.append("의 로그인 시간이 ");
    sb.append(zonedDateTime);
    sb.append("로 변경되었습니다.");
    this.message = sb.toString();
  }
}
