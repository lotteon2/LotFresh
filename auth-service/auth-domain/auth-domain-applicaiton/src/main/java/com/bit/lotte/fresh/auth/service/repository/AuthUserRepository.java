package com.bit.lotte.fresh.auth.service.repository;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;


public interface AuthUserRepository {
  AuthUser getAuthUser(AuthUserId authUserId);
  AuthUser createAuthUser(AuthUser authUser);
  void deleteAuthUser(AuthUserId authUserId);
  AuthUser updateRole(AuthUserId authUserId, AuthRole authRole);
  AuthUser updateTheLastLogin(AuthUserId authUserId, ZonedDateTime lastLoginTime);
}
