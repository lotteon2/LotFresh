package com.bit.lotte.fresh.auth.mapper;

import com.bit.lotte.fresh.auth.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.dto.command.LoginAuthDomainCommand;
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
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import java.time.ZonedDateTime;

public class AuthUserMapper {


  public CreateInitAuthUserResponse createAuthUserDomainEventToInitAuthUserResponse(
      CreateAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new CreateInitAuthUserResponse(authUser.getId(), authUser.getAuthProvider());
  }

  public AuthUser createAuthDomainCommandToAuthUser(CreateAuthDomainCommand command) {
    return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider())
        .id(command.getAuthUserId()).build();
  }

  public AuthUser loginAuthDomainCommandToUser(LoginAuthDomainCommand command) {
    if (command.getAuthProvider().equals(AuthProvider.NONE)) {
      return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider())
          .id(command.getAuthUserId()).build();
    }
    return (AuthUser) AuthUser.builder().id(command.getAuthUserId())
        .authProvider(command.getAuthProvider()).build();
  }

  public DeleteAuthUserResponse deleteAuthUserDomainEventToResponse(DeleteAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new DeleteAuthUserResponse(authUser.getId(), authUser.getAuthProvider());
  }

  public LogOutAuthUserResponse logOutAuthDomainEventToResponse(LogoutAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new LogOutAuthUserResponse(authUser.getId(), authUser.getAuthProvider());
  }

  public UpdateAuthUserRoleResponse updateAuthUserRoleToResponse(UpdateUserAuthRoleDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new UpdateAuthUserRoleResponse(authUser.getId(), authUser.getAuthProvider(),
        authUser.getUserRole());
  }

  public UpdateLoginSessionTimeResponse extendAuthUserLoginSessionEventToResponse(
      LoginSessionExtendAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new UpdateLoginSessionTimeResponse(authUser.getId(),authUser.getAuthProvider(),
        ZonedDateTime.now().plusHours(authUser.getLOGIN_SESSION_HOUR_TIME()));
  }
}
