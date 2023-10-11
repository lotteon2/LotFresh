package com.bit.lotte.fresh.app.dto;

import com.bit.lotte.fresh.app.valueobject.AuthenticationProvider;
import javax.validation.constraints.NotNull;

public class CreateAuthUserCommand {
  @NotNull
  AuthenticationProvider provider;
  @NotNull
  Long authId;
}
