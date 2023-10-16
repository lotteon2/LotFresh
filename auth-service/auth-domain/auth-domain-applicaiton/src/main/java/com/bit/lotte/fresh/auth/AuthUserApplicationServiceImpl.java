package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.mapper.AuthUserMapper;
import com.bit.lotte.fresh.auth.port.input.AuthUserApplicationService;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUserApplicationServiceImpl implements
    AuthUserApplicationService {

  private final AuthUserCommandHandler commandHandler;
  private final AuthUserMapper mapper;

  @Override
  public LoginAuthUserResponse oauthLoginAuthUser(OauthLoginDomainCommand command) {
    LoginAuthDomainEvent event = commandHandler.login(command);
    return mapper.loginEventToResponse(event);
  }

  @Override
  public CreateAuthUserResponse createAuthUser(CreateAuthDomainCommand command) {
    CreateAuthDomainEvent event = commandHandler.createOauthUser(command);
    return mapper.createAuthUserDomainEventToInitAuthUserResponse(event);
  }

  @Override
  public DeleteAuthUserResponse deleteAuthUser(AuthUserIdCommand command) {
    DeleteAuthDomainEvent event = commandHandler.deleteAuthUser(command);
    return mapper.deleteAuthUserDomainEventToResponse(event);
  }

  @Override
  public LogOutAuthUserResponse logOutAuthUser(AuthUserIdCommand id) {
    LogoutAuthDomainEvent event = commandHandler.logout(id);
    return mapper.logOutAuthDomainEventToResponse(event);

  }

  @Override
  public UpdateAuthUserRoleResponse updateRole(UpdateAuthRoleCommand command) {
    UpdateUserAuthRoleDomainEvent event = commandHandler.updateRole(
        new UpdateAuthRoleCommand(command.getActorId(), command.getTargetId(),
            command.getTargetRole()));
    return mapper.updateAuthUserRoleToResponse(event);
  }

  @Override
  public UpdateAuthUserRoleResponse updateUserToInitCategoryAdmin(UpdateAuthRoleCommand command,
      int categoryAdminId) {
    UpdateUserAuthRoleDomainEvent event = commandHandler.updateInitCategoryAdminRole(
        new UpdateAuthRoleCommand(command.getActorId(), command.getTargetId(), command.getTargetRole()),
        categoryAdminId);
    return mapper.updateAuthUserRoleToResponse(event);
  }

  @Override
  public UpdateLoginSessionTimeResponse extendLoginTime(AuthUserIdCommand id) {
    LoginSessionExtendAuthDomainEvent event = commandHandler.loginSessionExtend(id);
    return mapper.extendAuthUserLoginSessionEventToResponse(event);
  }
}
