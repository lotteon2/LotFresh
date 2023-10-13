package com.bit.lotte.fresh.auth;

import com.bit.lotte.fresh.auth.entity.AuthUserAbstract;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public interface CustomUserDetailService {
  public AuthUserAbstract loadByUserName(AuthUserId authUserId);
}
