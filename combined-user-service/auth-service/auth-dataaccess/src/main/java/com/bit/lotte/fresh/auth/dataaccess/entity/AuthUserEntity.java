package com.bit.lotte.fresh.auth.dataaccess.entity;

import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.sun.istack.NotNull;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class AuthUserEntity {

  @Id
  @NotNull
  private Long authId;
  private String email;
  private String password;
  private String description;
  @NotNull
  private AuthRole userRole;
  @NotNull
  private AuthProvider authProvider;
  private ZonedDateTime lastLoginTime;


}
