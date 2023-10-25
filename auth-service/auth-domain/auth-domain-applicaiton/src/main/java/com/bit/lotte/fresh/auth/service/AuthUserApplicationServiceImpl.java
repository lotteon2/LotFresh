package com.bit.lotte.fresh.auth.service;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.AdminInfoAuthDomainEvent;
import com.bit.lotte.fresh.auth.service.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.UpdateAuthRoleCommand;
import com.bit.lotte.fresh.auth.service.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.GetAdminInfoListResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.service.mapper.AuthUserMapper;
import com.bit.lotte.fresh.auth.service.port.input.AuthUserApplicationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUserApplicationServiceImpl implements
    AuthUserApplicationService {

  private final AuthUserCommandHandler commandHandler;
  private final AuthUserMapper mapper;

  @Override
  public LoginAuthUserResponse oauthLoginAuthUser(LoginAuthDomainCommand command) {
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
  public UpdateAuthUserRoleResponse updateCategoryAdmin(UpdateAuthRoleCommand command,
      int categoryAdminId) {
    UpdateUserAuthRoleDomainEvent event = commandHandler.updateCategoryAdmin(
        new UpdateAuthRoleCommand(command.getActorId(), command.getTargetId(), command.getTargetRole()),
        categoryAdminId);
    return mapper.updateAuthUserRoleToResponse(event);
  }

  @Override
  public UpdateLoginSessionTimeResponse extendLoginTime(AuthUserIdCommand id) {
    LoginSessionExtendAuthDomainEvent event = commandHandler.loginSessionExtend(id);
    return mapper.extendAuthUserLoginSessionEventToResponse(event);
  }

  @Override
  public GetAdminInfoListResponse getAuthUserList() {
    List<AdminInfoAuthDomainEvent> eventList = commandHandler.getAllAuthUser();
    List<AuthUser> returnAuthUserList = mapper.adminInfoEventListToAuthUserList(eventList);
    return new GetAdminInfoListResponse(returnAuthUserList);
  }
}
