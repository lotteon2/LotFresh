package com.bit.lotte.fresh.user.common.valueobject;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthUserId extends BaseId<Long> {

  public AuthUserId(Long value) {
    super(value);
  }

  @JsonCreator
  public static AuthUserId fromJson(@JsonProperty("value") Long value) {
    return new AuthUserId(value);
  }
}
