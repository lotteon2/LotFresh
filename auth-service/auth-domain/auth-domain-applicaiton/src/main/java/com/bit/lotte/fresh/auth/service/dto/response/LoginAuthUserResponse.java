package com.bit.lotte.fresh.auth.service.dto.response;


import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter

public class LoginAuthUserResponse {
  private final AuthUserId authUserId;
  private final AuthProvider authProvider;
  private String message;


  @JsonCreator
  public LoginAuthUserResponse(
      @JsonProperty("authUserId") AuthUserId authUserId,
      @JsonProperty("authProvider:") AuthProvider authProvider) {
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
