package com.bit.lotte.fresh.auth.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract public class OauthUserInfo {

  private Long id;

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

}
