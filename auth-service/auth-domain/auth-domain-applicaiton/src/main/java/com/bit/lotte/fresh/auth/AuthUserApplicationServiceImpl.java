package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateInitAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateAuthDomainRoleEvent;
import com.bit.lotte.fresh.auth.mapper.AuthUserMapper;
import com.bit.lotte.fresh.auth.port.input.AuthUserApplicationService;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUserApplicationServiceImpl implements
    AuthUserApplicationService {

  private final AuthUserCommandHandler commandHandler;
  private final AuthUserMapper mapper;

  @Override
  public CreateInitAuthUserResponse createAuthUser(CreateAuthDomainCommand command) {
    CreateAuthDomainEvent event = commandHandler.createAuthUser(command);
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
  public UpdateAuthUserRoleResponse updateRole(AuthUserIdCommand actor, AuthUserIdCommand target) {
     UpdateAuthDomainRoleEvent event = commandHandler.updateRole(actor,target);
     return mapper.updateAuthUserRoleToResponse(event);
  }

  @Override
  public UpdateLoginSessionTimeResponse extendLoginTime(AuthUserIdCommand id) {
    LoginSessionExtendAuthDomainEvent event = commandHandler.loginSessionExtend(id);
    return mapper.extendAuthUserLoginSessionEventToResponse(event);
  }
}
