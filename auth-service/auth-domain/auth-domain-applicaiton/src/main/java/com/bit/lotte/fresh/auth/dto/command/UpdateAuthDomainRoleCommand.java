package com.bit.lotte.fresh.auth.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;

public class UpdateAuthDomainRoleCommand {

  @NotNull
  AuthUserId authUserId;
  @NotNull
  AuthRole authRole;

}
