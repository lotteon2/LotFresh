package com.bit.lotte.fresh.dataaccess.mapper;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.dataaccess.adapter.AuthUserAdapter;
import com.bit.lotte.fresh.dataaccess.entity.AuthUserEntity;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.stereotype.Component;

public class AuthUserDataAccessMapper {


  public AuthUserDataAccessMapper() {
  }

  public AuthUserAdapter authUserToAuthUserAdapter(AuthUser authUser) {
    return AuthUserAdapter.builder().authUser(authUser).build();
  }

  public AuthUser authEntityToAuthUser(AuthUserEntity authUser) {
    return AuthUser.builder().id(new AuthUserId(Long.valueOf(authUser.getAuthId())))
        .authProvider(authUser.getAuthProvider())
        .description(authUser.getDescription()).email(authUser.getEmail())
        .password(authUser.getPassword()).lastLoginTime(authUser.getLastLoginTime())
        .userRole(authUser.getUserRole()).build();
  }

  public AuthUserEntity authUserToAuthEntity(AuthUser authUser) {
    return  AuthUserEntity.builder().authId(Long.valueOf(authUser.getId().getValue()))
        .authProvider(authUser.getAuthProvider())
        .description(authUser.getDescription()).email(authUser.getEmail())
        .password(authUser.getPassword()).lastLoginTime(authUser.getLastLoginTime())
        .userRole(authUser.getUserRole()).build();
  }
}
