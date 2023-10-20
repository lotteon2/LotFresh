package com.bit.lotte.fresh.auth.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;

public class OauthLoginDomainCommand extends
    LoginAuthDomainCommand {

  public OauthLoginDomainCommand(
      @NotNull AuthUserId authUserId,
      @NotNull AuthProvider authProvider) {
    super(authUserId, authProvider);
  }
}
