package com.bit.lotte.fresh.user.common.message;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateAuthUserCommand {
  @JsonProperty("authUserId")
  @NotNull
  AuthUserId authUserId;
  @JsonProperty("authProvider")
  @NotNull
  AuthProvider authProvider;


}
