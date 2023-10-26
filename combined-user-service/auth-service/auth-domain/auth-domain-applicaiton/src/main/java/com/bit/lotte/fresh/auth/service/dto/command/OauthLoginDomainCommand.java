package com.bit.lotte.fresh.auth.service.dto.command;


import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;


public class OauthLoginDomainCommand extends
    LoginAuthDomainCommand {

  public OauthLoginDomainCommand() {
  }
  public OauthLoginDomainCommand(
      @NotNull AuthUserId authUserId,
      @NotNull AuthProvider authProvider) {
    super(authUserId, authProvider);
  }

}
