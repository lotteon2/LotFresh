package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.DeleteAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LoginSessionExtendAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.LogoutAuthDomainEvent;
import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;


public interface AuthDomainApplicationService {

  CreateAuthDomainEvent createAuthUser(AuthUser authUser);

  DeleteAuthDomainEvent deleteAuthUser(AuthUser authUser);

  LoginAuthDomainEvent login(AuthUser authUser);

  LoginSessionExtendAuthDomainEvent checkSessionIsExpired(AuthUser authUser);

  LogoutAuthDomainEvent logout(AuthUser authUser);

  UpdateUserAuthRoleDomainEvent updateRole(AuthUser actor, AuthUser target);


}
