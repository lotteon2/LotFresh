package com.bit.lotte.fresh.auth.service.dto.command;


import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthUserIdCommand {

  public AuthUserIdCommand(AuthUserId authUserId) {
    this.authUserId = authUserId;
  }

  @NotNull AuthUserId authUserId;
}
