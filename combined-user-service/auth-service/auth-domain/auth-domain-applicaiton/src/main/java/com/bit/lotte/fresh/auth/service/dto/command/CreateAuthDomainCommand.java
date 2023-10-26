package com.bit.lotte.fresh.auth.service.dto.command;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthDomainCommand {

  @NotNull
  private AuthUserId authUserId;
  @NotNull
 private AuthProvider authProvider;


}
