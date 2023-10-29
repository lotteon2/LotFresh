package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateAuthUserRoleResponse {
  private  AuthUserId authUserId;
  private  AuthRole authRole;
  private String message;
@JsonCreator
  public UpdateAuthUserRoleResponse(
      @JsonProperty("authUserId") AuthUserId authUserId,
      @JsonProperty("authRole") AuthRole authRole) {
    this.authUserId = authUserId;
    this.authRole = authRole;
    this.getMessage();
  }

  void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(authUserId);
    sb.append("님의 ");
    sb.append("의 권한이");
    sb.append(authRole);
    sb.append("로 변경되었습니다.");
    this.message = sb.toString();
  }
}
