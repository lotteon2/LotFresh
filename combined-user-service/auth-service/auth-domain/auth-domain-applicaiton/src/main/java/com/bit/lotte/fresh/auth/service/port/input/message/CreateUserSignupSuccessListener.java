package com.bit.lotte.fresh.auth.service.port.input.message;

import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;

public interface CreateUserSignupSuccessListener {
  void listen(AuthUserId authUserId);
}
