package com.bit.lotte.fresh.auth.dataaccess.mapper;

import com.bit.lotte.fresh.auth.dataaccess.adapter.AuthUserAdaptor;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.dataaccess.entity.AuthUserEntity;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDataAccessMapper {


  public AuthUserDataAccessMapper() {
  }

  public AuthUserAdaptor authUserToAuthUserAdapter(AuthUser authUser) {
    return AuthUserAdaptor.builder().authUser(authUser).build();
  }

  public AuthUser authEntityToAuthUser(AuthUserEntity authUser) {
    return AuthUser.builder().entityId(new AuthUserId(authUser.getAuthId()))
        .authProvider(authUser.getAuthProvider())
        .description(authUser.getDescription()).email(authUser.getEmail())
        .password(authUser.getPassword()).lastLoginTime(authUser.getLastLoginTime())
        .userRole(authUser.getUserRole()).build();
  }

  public AuthUserEntity authUserToAuthEntity(AuthUser authUser) {
    return  AuthUserEntity.builder().authId(Long.valueOf(authUser.getEntityId().getValue()))
        .authProvider(authUser.getAuthProvider())
        .description(authUser.getDescription()).email(authUser.getEmail())
        .password(authUser.getPassword()).lastLoginTime(authUser.getLastLoginTime())
        .userRole(authUser.getUserRole()).build();
  }
}
