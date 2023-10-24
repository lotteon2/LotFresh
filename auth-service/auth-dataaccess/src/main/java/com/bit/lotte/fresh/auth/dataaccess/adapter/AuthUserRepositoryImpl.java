package com.bit.lotte.fresh.auth.dataaccess.adapter;

import com.bit.lotte.fresh.auth.dataaccess.entity.AuthUserEntity;
import com.bit.lotte.fresh.auth.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.auth.dataaccess.repository.AuthUserEntityJpaRepository;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.service.repository.AuthUserRepository;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthUserRepositoryImpl implements AuthUserRepository {

  private final AuthUserEntityJpaRepository repository;
  private final AuthUserDataAccessMapper mapper;


  @Override
  public AuthUser getAuthUser(AuthUserId authUserId) {
    AuthUserEntity authUserEntity  =repository.findById(authUserId.getValue()).orElseThrow(()->{
      throw new AuthUserDomainException("존재하지 않는 회원입니다.");
    });
    return mapper.authEntityToAuthUser(authUserEntity);
  }

  @Override
  public AuthUser createAuthUser(AuthUser authUser) {
    return mapper.authEntityToAuthUser(repository.save(mapper.authUserToAuthEntity(authUser)));
  }

  @Override
  public void deleteAuthUser(AuthUserId authUserId) {
    repository.deleteById(authUserId.getValue());
  }

  @Override
  public AuthUser updateRole(AuthUserId authUserId, AuthRole authRole) {
    AuthUser authUser = getAuthUser(authUserId);
    authUser.setUserRole(authRole);
    repository.save(mapper.authUserToAuthEntity(authUser));
    return authUser;
  }

  @Override
  public AuthUser updateTheLastLogin(AuthUserId authUserId, ZonedDateTime lastLoginTime) {
    AuthUser authUser = getAuthUser(authUserId);
    authUser.setLastLoginTime(lastLoginTime);
    repository.save(mapper.authUserToAuthEntity(authUser));
    return authUser;
  }
}
