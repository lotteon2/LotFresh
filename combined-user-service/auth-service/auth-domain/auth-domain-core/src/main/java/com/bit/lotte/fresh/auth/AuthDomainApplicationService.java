package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;


public interface AuthDomainApplicationService {


  UpdateUserAuthRoleDomainEvent updateCategoryAdminSubIdList(AuthUser authUser,
      String subIdListWithString);

  CreateAuthDomainEvent createOauthUser(AuthUser authUser);

  DeleteAuthDomainEvent deleteAuthUser(AuthUser authUser);

  LoginAuthDomainEvent login(AuthUser authUser);

  LogoutAuthDomainEvent logout(AuthUser authUser);

  UpdateUserAuthRoleDomainEvent updateRole(AuthUser actor, AuthUser target,
      AuthRole targetUpdatedRole);

  UpdateUserAuthRoleDomainEvent updateCategoryAdmin(AuthUser actor, AuthUser target,
      AuthRole targetRole,
      String categoryId);

  LoginSessionExtendAuthDomainEvent loginExtend(AuthUser actor);


}
