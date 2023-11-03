package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateUserCommand {

  @NotNull
  private  UserId userId;
  @NotNull
  private  String name;
  @NotNull
  private  String contact;
  @NotNull
  private  Gender gender;
}