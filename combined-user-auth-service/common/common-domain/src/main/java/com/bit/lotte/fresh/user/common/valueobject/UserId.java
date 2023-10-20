package com.bit.lotte.fresh.user.common.valueobject;


import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class UserId extends BaseId<Long>{

  public UserId(Long value) {
    super(value);
  }
}
