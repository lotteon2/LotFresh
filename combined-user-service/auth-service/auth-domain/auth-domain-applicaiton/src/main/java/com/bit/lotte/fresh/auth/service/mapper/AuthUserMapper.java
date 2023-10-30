package com.bit.lotte.fresh.auth.service.mapper;

import com.bit.lotte.fresh.auth.event.AdminInfoAuthDomainEvent;
import com.bit.lotte.fresh.auth.service.dto.command.CreateAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.response.CreateAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.DeleteAuthUserResponse;
import com.bit.lotte.fresh.auth.service.dto.response.GetAdminInfoListResponse;
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
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AuthUserMapper {


  public LoginAuthUserResponse loginEventToResponse(LoginAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new LoginAuthUserResponse(authUser.getEntityId(),authUser.getAuthProvider());
  }

  public CreateAuthUserResponse createAuthUserDomainEventToInitAuthUserResponse(
      CreateAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new CreateAuthUserResponse(authUser.getEntityId(), authUser.getAuthProvider());
  }

  public AuthUser createAuthDomainCommandToAuthUser(CreateAuthDomainCommand command) {
    return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider())
        .entityId(command.getAuthUserId()).build();
  }

  public AuthUser loginAuthDomainCommandToUser(LoginAuthDomainCommand command) {
    if (command.getAuthProvider().equals(AuthProvider.NONE)) {
      return (AuthUser) AuthUser.builder().authProvider(command.getAuthProvider())
          .entityId(command.getAuthUserId()).build();
    }
    return (AuthUser) AuthUser.builder().entityId(command.getAuthUserId())
        .authProvider(command.getAuthProvider()).build();
  }

  public DeleteAuthUserResponse deleteAuthUserDomainEventToResponse(DeleteAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new DeleteAuthUserResponse(authUser.getEntityId(), authUser.getAuthProvider());
  }

  public LogOutAuthUserResponse logOutAuthDomainEventToResponse(LogoutAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new LogOutAuthUserResponse(authUser.getEntityId(), authUser.getAuthProvider());
  }

  public UpdateAuthUserRoleResponse updateAuthUserRoleToResponse(UpdateUserAuthRoleDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new UpdateAuthUserRoleResponse(authUser.getEntityId(),
        authUser.getUserRole());
  }

  public UpdateLoginSessionTimeResponse extendAuthUserLoginSessionEventToResponse(
      LoginSessionExtendAuthDomainEvent event) {
    AuthUser authUser = event.getAuthUser();
    return new UpdateLoginSessionTimeResponse(authUser.getEntityId(),authUser.getAuthProvider(),
        ZonedDateTime.now().plusHours(authUser.getLOGIN_SESSION_HOUR_TIME()));
  }


  public List<AuthUser> adminInfoEventListToAuthUserList(List<AdminInfoAuthDomainEvent> eventList) {
    List<AuthUser> authUserList = new ArrayList<>();
    for (AdminInfoAuthDomainEvent event : eventList) {
      authUserList.add(event.getAuthUser());
    }
    return authUserList;
  }

  public List<GetAdminInfoListResponse> domainToGetAdminInfoListResponse(
      List<AuthUser> returnAuthUserList) {
    List<GetAdminInfoListResponse> responseList = new ArrayList<>();
    for (AuthUser authUser : returnAuthUserList) {
      responseList.add(new GetAdminInfoListResponse(authUser.getEntityId(), authUser.getUserRole(),
          authUser.getDescription(), authUser.getAuthProvider(), authUser.getLastLoginTime()));
    }
    return responseList;
  }
}
