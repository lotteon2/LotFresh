package com.bit.lotte.fresh.auth.port.input;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;

public interface AuthUserApplicationService {
  LoginAuthUserResponse oauthLoginAuthUser(OauthLoginDomainCommand command);
  CreateAuthUserResponse createAuthUser(CreateAuthDomainCommand command);
  DeleteAuthUserResponse deleteAuthUser(AuthUserIdCommand id);
  LogOutAuthUserResponse logOutAuthUser(AuthUserIdCommand id);
  UpdateAuthUserRoleResponse updateRole(UpdateAuthRoleCommand command);
  UpdateAuthUserRoleResponse updateUserToInitCategoryAdmin(UpdateAuthRoleCommand command, int categoryAdminId);
  UpdateLoginSessionTimeResponse extendLoginTime(AuthUserIdCommand id);

}
