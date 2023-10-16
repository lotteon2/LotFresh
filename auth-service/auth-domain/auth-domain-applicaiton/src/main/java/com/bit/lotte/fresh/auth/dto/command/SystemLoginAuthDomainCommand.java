package com.bit.lotte.fresh.auth.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;

public class SystemLoginAuthDomainCommand extends
    LoginAuthDomainCommand {

  private final String email;
  private final String password;

  public SystemLoginAuthDomainCommand(
      @NotNull AuthUserId authUserId,
      @NotNull AuthProvider authProvider, String email, String password) {
    super(authUserId, authProvider);
    this.email = email;
    this.password = password;
  }
}
