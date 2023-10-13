package com.bit.lotte.fresh.auth.repository;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public interface AuthUserRepository {
  AuthUser getAuthUser(AuthUserId authUserId);
  AuthUser createAuthUser(AuthUser authUser);
  AuthUser deleteAuthUser(AuthUserId authUserId);
  AuthUser updateRole(AuthUserId authUserId, AuthRole authRole);
  AuthUser updateTheLastLogin(AuthUserId authUserId);
}
