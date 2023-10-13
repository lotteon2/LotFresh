package com.bit.lotte.fresh.auth.dto.command;


import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthUserIdCommand {
  @NotNull AuthUserId authUserId;
}
