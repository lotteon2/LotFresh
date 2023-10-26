package com.bit.lotte.fresh.auth.service.dto.command;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UpdateAuthRoleCommand {

  @NotNull private final AuthUserId actorId;
  @NotNull private final AuthUserId targetId;
  @NotNull private final AuthRole targetRole;

}
