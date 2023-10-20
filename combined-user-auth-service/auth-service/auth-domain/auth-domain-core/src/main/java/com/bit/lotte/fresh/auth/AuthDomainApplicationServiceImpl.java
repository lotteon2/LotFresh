package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDomainApplicationServiceImpl implements
    AuthDomainApplicationService {

  @Override
  public CreateAuthDomainEvent createOauthUser(AuthUser authUser) {
    authUser.oauthUserCreation(authUser.getId(), AuthProvider.KAKAO);
    return new CreateAuthDomainEvent(authUser,ZonedDateTime.now());
  }

  @Override
  public DeleteAuthDomainEvent deleteAuthUser(AuthUser authUser) {
    return new DeleteAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public LoginAuthDomainEvent login(AuthUser authUser) {
    authUser.loginProcessor(authUser.getId(), authUser.getEmail(), authUser.getPassword(),
        authUser.getAuthProvider());
    return new LoginAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public LoginSessionExtendAuthDomainEvent checkSessionIsExpired(AuthUser authUser) {
    authUser.extendLoginSession(authUser.getId());
    return new LoginSessionExtendAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public LogoutAuthDomainEvent logout(AuthUser authUser) {
    authUser.logOut(authUser.getId());
    return new LogoutAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public UpdateUserAuthRoleDomainEvent updateRole(
      AuthUser actor, AuthUser target) {
    actor.updateAdminAuthorization(actor.getUserRole(), actor.getDescription(),
        target.getUserRole(),
        target.getDescription());
    return new UpdateUserAuthRoleDomainEvent(target, ZonedDateTime.now());
  }
}
