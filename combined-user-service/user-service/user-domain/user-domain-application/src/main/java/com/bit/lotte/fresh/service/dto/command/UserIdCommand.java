package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.user.common.valueobject.UserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdCommand {
  @NotNull
  private UserId userId;

    @JsonCreator
  public static UserIdCommand fromJson(@JsonProperty("userId") UserId userId) {
    return new UserIdCommand(userId);
  }

}
