package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserCommand {
  @NotNull
  private final UserId userId;
  @NotNull
  private final String name;
  @NotNull
  private final String contact;
  @NotNull
  private final Gender gender;
  @NotNull
  private final Address defaultAddress;
}
