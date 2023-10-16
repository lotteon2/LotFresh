package com.bit.lotte.fresh.dataaccess.entity;

import com.bit.lotte.fresh.auth.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.sun.istack.NotNull;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;


@Entity
@Builder
@Getter
public class AuthUserEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.TABLE
  )
  private Long authId;
  private String email;
  private String password;
  @NotNull
  private AuthRole userRole;
  @NotNull
  private String description;
  @NotNull
  private AuthProvider authProvider;
  @NotNull
  private ZonedDateTime lastLoginTime;


}
