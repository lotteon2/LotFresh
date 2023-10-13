package com.bit.lotte.fresh.message.listener;

import com.bit.lotte.fresh.auth.port.input.message.CreateUserSignupSuccessListener;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.stereotype.Component;

@Component
public class CreateUserSignupSuccessListenerImpl implements
    CreateUserSignupSuccessListener {


  @Override
  public void listen(AuthUserId authUserId) {

  }
}
