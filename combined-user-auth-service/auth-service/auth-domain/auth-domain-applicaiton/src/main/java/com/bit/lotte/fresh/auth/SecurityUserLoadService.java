package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public interface SecurityUserLoadService {
  public AuthUser loadUser(AuthUserId authId);
}
