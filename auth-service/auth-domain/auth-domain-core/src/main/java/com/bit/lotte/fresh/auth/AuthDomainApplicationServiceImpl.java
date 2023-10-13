package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateAuthDomainRoleEvent;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDomainApplicationServiceImpl implements
    AuthDomainApplicationService {

  @Override
  public CreateAuthDomainEvent createAuthUser(AuthUser authUserAbstract) {
    return new CreateAuthDomainEvent(
        AuthUserAbstract.InitCreation(authUserAbstract.getId(), authUserAbstract.getEmail(),
        authUserAbstract.getPassword(), authUserAbstract.getAuthProvider()),
        ZonedDateTime.now());
  }

  @Override
  public DeleteAuthDomainEvent deleteAuthUser(AuthUser authUserAbstract) {
    return new DeleteAuthDomainEvent(authUserAbstract, ZonedDateTime.now());
  }

  @Override
  public LoginAuthDomainEvent login(AuthUser authUserAbstract) {
    return new LoginAuthDomainEvent(
        authUserAbstract.loginProcessor(authUserAbstract.getId(), authUserAbstract.getEmail(),
        authUserAbstract.getPassword(), authUserAbstract.getAuthProvider()), ZonedDateTime.now());
  }

  @Override
  public LoginSessionExtendAuthDomainEvent checkSessionIsExpired(AuthUser authUserAbstract) {
    authUserAbstract.extendLoginSession(authUserAbstract.getId());
    return new LoginSessionExtendAuthDomainEvent(authUserAbstract, ZonedDateTime.now());
  }

  @Override
  public LogoutAuthDomainEvent logout(AuthUser authUserAbstract) {

    authUserAbstract.logOut(authUserAbstract.getId());
    return new LogoutAuthDomainEvent(authUserAbstract, ZonedDateTime.now());
  }

  @Override
  public UpdateAuthDomainRoleEvent updateRole(
      AuthUser authUserAbstract, AuthUser target) {
    authUserAbstract.updateAdminAuthorization(authUserAbstract.getUserRole(), authUserAbstract.getDescription(),
        target.getUserRole(),
        target.getDescription());
    return new UpdateAuthDomainRoleEvent(target,ZonedDateTime.now());
  }
}
