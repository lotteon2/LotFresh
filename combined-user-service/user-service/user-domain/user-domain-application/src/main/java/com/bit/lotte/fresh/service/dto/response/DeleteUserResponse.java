package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteUserResponse {

  @NotNull
  private final UserId userId;
  @NotNull
  private String message;

  public DeleteUserResponse(User user) {
    this.userId = user.getEntityId();
    getMessage(user);
  }

  private void getMessage(User user) {
    StringBuilder sb = new StringBuilder();
    sb.append(user.getName() + "님의 계정탈퇴가 완료되었습니다.");
    this.message = sb.toString();
  }
}
