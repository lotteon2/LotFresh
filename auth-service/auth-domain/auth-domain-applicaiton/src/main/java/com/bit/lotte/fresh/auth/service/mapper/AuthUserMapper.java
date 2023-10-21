package com.bit.lotte.fresh.auth.service.mapper;

import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LogOutAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateAuthUserRoleResponse;
import com.bit.lotte.fresh.auth.service.dto.response.UpdateLoginSessionTimeResponse;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import java.time.ZonedDateTime;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AuthUserMapper {


  public LoginAuthUserResponse loginEventToResponse(LoginAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new LoginAuthUserResponse(authUser.getId(),authUser.getAuthProvider());
  }

  public CreateAuthUserResponse createAuthUserDomainEventToInitAuthUserResponse(
      CreateAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new CreateAuthUserResponse(authUser.getId(), authUser.getAuthProvider());
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
    return new UpdateAuthUserRoleResponse(authUser.getId(),
        authUser.getUserRole());
  }

  public UpdateLoginSessionTimeResponse extendAuthUserLoginSessionEventToResponse(
      LoginSessionExtendAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new UpdateLoginSessionTimeResponse(authUser.getId(),authUser.getAuthProvider(),
        ZonedDateTime.now().plusHours(authUser.getLOGIN_SESSION_HOUR_TIME()));
  }


}
