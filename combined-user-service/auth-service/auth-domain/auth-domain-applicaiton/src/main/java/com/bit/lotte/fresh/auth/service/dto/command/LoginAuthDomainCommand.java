package com.bit.lotte.fresh.auth.service.dto.command;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class LoginAuthDomainCommand {
 @NotNull
 private AuthUserId authUserId;
 @NotNull
 private AuthProvider authProvider;

 public LoginAuthDomainCommand(
     @JsonProperty("authUserId") AuthUserId authUserId,
     @JsonProperty("authProvider") AuthProvider authProvider) {
  this.authUserId = authUserId;
  this.authProvider = authProvider;
 }
}
