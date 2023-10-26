package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDomainApplicationServiceImpl implements
    AuthDomainApplicationService {

  @Override
  public UpdateUserAuthRoleDomainEvent updateCategoryAdminSubIdList(AuthUser authUser,
      String subIdListWithString) {
    authUser.setDescription(subIdListWithString);
    return new UpdateUserAuthRoleDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public CreateAuthDomainEvent createOauthUser(AuthUser authUser) {
    AuthUser createdAuthUser = authUser.oauthUserCreation(authUser.getEntityId(), AuthProvider.KAKAO);
    return new CreateAuthDomainEvent(createdAuthUser, ZonedDateTime.now());
  }

  @Override
  public DeleteAuthDomainEvent deleteAuthUser(AuthUser authUser) {
    return new DeleteAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public LoginAuthDomainEvent login(AuthUser authUser) {
    authUser.loginProcessor(authUser.getEntityId(), authUser.getEmail(), authUser.getPassword(),
        authUser.getAuthProvider());
    return new LoginAuthDomainEvent(authUser, ZonedDateTime.now());
  }


  @Override
  public LogoutAuthDomainEvent logout(AuthUser authUser) {
    authUser.logOut(authUser.getEntityId());
    return new LogoutAuthDomainEvent(authUser, ZonedDateTime.now());
  }

  @Override
  public UpdateUserAuthRoleDomainEvent updateRole(
      AuthUser actor, AuthUser target, AuthRole targetUpdatedRole) {
    target.updateAdminAuthorization(actor.getUserRole(), actor.getDescription(),
       targetUpdatedRole,
        target.getDescription());
    return new UpdateUserAuthRoleDomainEvent(target, ZonedDateTime.now());
  }

  @Override
  public UpdateUserAuthRoleDomainEvent updateCategoryAdmin(AuthUser actor, AuthUser target,
      AuthRole targetUpdatedRole, String categoryId) {

    target.setDescription(categoryId);
    target.updateAdminAuthorization(actor.getUserRole(), actor.getDescription(),
        targetUpdatedRole,
        target.getDescription());
    return new UpdateUserAuthRoleDomainEvent(target, ZonedDateTime.now());
  }


  @Override
  public LoginSessionExtendAuthDomainEvent loginExtend(AuthUser actor) {
    actor.extendLoginSession(actor.getEntityId());
    return new LoginSessionExtendAuthDomainEvent(actor, ZonedDateTime.now());
  }
}
