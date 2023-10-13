package com.bit.lotte.fresh.dataaccess.adapter;

import com.bit.lotte.fresh.auth.SecurityUserLoadService;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.repository.AuthUserRepository;
import com.bit.lotte.fresh.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServiceAdapter implements UserDetailsService, SecurityUserLoadService {

  private final AuthUserRepository authUserRepository;
  private final AuthUserDataAccessMapper mapper;
  @Override
  public AuthUserAdapter loadUserByUsername(String username) throws UsernameNotFoundException {
    return mapper.authUserToAuthUserAdapter(loadUser(new AuthUserId(Long.valueOf(username))));
  }

  @Override
  public AuthUser loadUser(AuthUserId authId) {
    return Optional.ofNullable(authUserRepository.getAuthUser(authId)).orElseThrow(
        ()-> new AuthUserDomainException("존재하지 않는 아이디입니다."));
  }
}
