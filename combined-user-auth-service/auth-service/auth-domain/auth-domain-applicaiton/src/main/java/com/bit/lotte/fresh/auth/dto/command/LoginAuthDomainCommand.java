package com.bit.lotte.fresh.auth.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class LoginAuthDomainCommand {

  @NotNull
  private final AuthUserId authUserId;
  @NotNull
  private final AuthProvider authProvider;


}
