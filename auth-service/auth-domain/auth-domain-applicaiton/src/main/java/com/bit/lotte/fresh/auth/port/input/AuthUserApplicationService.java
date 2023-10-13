package com.bit.lotte.fresh.auth.port.input;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateInitAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public interface AuthUserApplicationService {
  CreateInitAuthUserResponse createAuthUser(CreateAuthDomainCommand command);
  DeleteAuthUserResponse deleteAuthUser(AuthUserIdCommand id);
  LogOutAuthUserResponse logOutAuthUser(AuthUserIdCommand id);
  UpdateAuthUserRoleResponse updateRole(AuthUserIdCommand actor , AuthUserId target);
  UpdateLoginSessionTimeResponse extendLoginTime(AuthUserId id);

}
