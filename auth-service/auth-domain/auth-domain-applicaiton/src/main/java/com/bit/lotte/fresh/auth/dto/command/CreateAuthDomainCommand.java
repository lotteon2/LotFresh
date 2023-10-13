package com.bit.lotte.fresh.auth.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAuthDomainCommand {

  @NotNull
  private AuthUserId authUserId;
  @NotNull
 private AuthProvider authProvider;


}
