package com.bit.lotte.fresh.auth.service.dto.response;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetAdminInfoListResponse {
 private AuthUserId authUserId;
  private AuthRole authRole;
  private String categoryAdminSubDescription;
  private AuthProvider authProvider;
  private ZonedDateTime lastLoginTime;


  @JsonCreator
  public GetAdminInfoListResponse(
      @JsonProperty("authUserId") AuthUserId authUserId,
      @JsonProperty("authRole") AuthRole authRole,
      @JsonProperty("categoryAdminSubDescription") String categoryAdminSubDescription,
      @JsonProperty("authProvider") AuthProvider authProvider,
      @JsonProperty("lastLoginTime") ZonedDateTime lastLoginTime) {
    this.authUserId = authUserId;
    this.authRole = authRole;
    this.categoryAdminSubDescription = categoryAdminSubDescription;
    this.authProvider = authProvider;
    this.lastLoginTime = lastLoginTime;
  }
}
