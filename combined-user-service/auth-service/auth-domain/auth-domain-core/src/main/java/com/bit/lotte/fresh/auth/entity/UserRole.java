package com.bit.lotte.fresh.auth.entity;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.entity.BaseEntity;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class UserRole {

  private AuthRole role;
  private  String  description;

}


