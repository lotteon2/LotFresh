package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserIdCommand {
  @NotNull
  private UserId userId;
}
