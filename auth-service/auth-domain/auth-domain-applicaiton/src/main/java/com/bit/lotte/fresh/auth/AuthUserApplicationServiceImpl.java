package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.response.CreateInitAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
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
  public DeleteAuthUserResponse deleteAuthUser(AuthUserIdCommand id) {
    return null;
  }

  @Override
  public LogOutAuthUserResponse logOutAuthUser(AuthUserIdCommand id) {
    return null;
  }

  @Override
  public UpdateAuthUserRoleResponse updateRole(AuthUserIdCommand actor, AuthUserId target) {
    return null;
  }

  @Override
  public UpdateLoginSessionTimeResponse extendLoginTime(AuthUserId id) {
    return null;
  }
}
