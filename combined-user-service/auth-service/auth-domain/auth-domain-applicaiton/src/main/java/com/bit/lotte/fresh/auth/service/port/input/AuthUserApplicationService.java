package com.bit.lotte.fresh.auth.service.port.input;

import com.bit.lotte.fresh.auth.service.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.service.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.GetAdminInfoListResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateLoginSessionTimeResponse;
import java.util.List;

public interface AuthUserApplicationService {
  LoginAuthUserResponse loginAuthUser(LoginAuthDomainCommand command);
  CreateAuthUserResponse createAuthUser(CreateAuthDomainCommand command);
  DeleteAuthUserResponse deleteAuthUser(AuthUserIdCommand id);
  LogOutAuthUserResponse logOutAuthUser(AuthUserIdCommand id);
  UpdateAuthUserRoleResponse updateRole(UpdateAuthRoleCommand command);
  UpdateAuthUserRoleResponse updateCategoryAdmin(UpdateAuthRoleCommand command, int categoryAdminId);
  UpdateLoginSessionTimeResponse extendLoginTime(AuthUserIdCommand id);
  List<GetAdminInfoListResponse> getAuthUserList();
}
