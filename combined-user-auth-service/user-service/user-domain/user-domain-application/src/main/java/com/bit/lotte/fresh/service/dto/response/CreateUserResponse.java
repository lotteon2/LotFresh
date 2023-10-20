package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserResponse {

  @NotNull
  private final UserId userId;
  @NotNull
  private String message;

  public CreateUserResponse(User user) {
    this.userId = user.getId();
    this.getMessage();
  }

  private void getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append("회원가입이 성공적으로 수행되었습니다.: ");
    this.message = sb.toString();
  }

}
