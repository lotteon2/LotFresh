package com.bit.lotte.fresh.user.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserId extends BaseId<Long> {

  public UserId(Long value) {
    super(value);
  }

  @JsonCreator
  public static UserId fromJson(@JsonProperty("value") Long value) {
    return new UserId(value);
  }
}
