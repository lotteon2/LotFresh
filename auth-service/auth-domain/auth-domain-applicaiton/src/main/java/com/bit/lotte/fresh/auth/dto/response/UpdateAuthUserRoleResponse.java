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
public class UpdateAuthUserRoleResponse {
  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private final AuthRole authRole;
  private String message;

  public UpdateAuthUserRoleResponse(AuthUserId authUserId,
      AuthProvider authProvider,
      AuthRole authRole) {
    this.authUserId = authUserId;
    this.authProvider = authProvider;
    this.authRole = authRole;
    this.getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append(authProvider);
    sb.append("의 권한이");
    sb.append(authRole);
    sb.append("로 변경되었습니다.");
    this.message = sb.toString();
  }
}
