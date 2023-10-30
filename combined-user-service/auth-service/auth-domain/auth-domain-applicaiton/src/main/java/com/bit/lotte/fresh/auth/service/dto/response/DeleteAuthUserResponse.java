package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeleteAuthUserResponse {

  @NotNull
  private AuthUserId authUserId;
  @NotNull
  private AuthProvider authProvider;
  private String message;

  @JsonCreator
  public DeleteAuthUserResponse(
      @JsonProperty("authUserId") AuthUserId authUserId,
      @JsonProperty("authProvider") AuthProvider authProvider) {
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
