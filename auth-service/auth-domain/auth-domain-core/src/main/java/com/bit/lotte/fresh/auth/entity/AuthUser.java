package com.bit.lotte.fresh.auth.entity;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;

public class AuthUser extends AuthUserAbstract {

  AuthUser(AuthUserId id, String email,
      String password, AuthRole userRole,
      String description, boolean isLoginSessionExpired,
      AuthProvider authProvider,
      ZonedDateTime lastLoginTime) {
    super(id, email, password, userRole, description, isLoginSessionExpired, authProvider,
        lastLoginTime);
  }

}

