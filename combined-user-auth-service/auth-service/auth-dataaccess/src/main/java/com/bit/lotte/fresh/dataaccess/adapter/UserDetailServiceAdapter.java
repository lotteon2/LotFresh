package com.bit.lotte.fresh.dataaccess.adapter;

import com.bit.lotte.fresh.auth.SecurityUserLoadService;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.dataaccess.repository.AuthUserEntityJpaRepository;
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

  private final AuthUserEntityJpaRepository authUserEntityJpaRepository;
  private final AuthUserDataAccessMapper mapper;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return mapper.authUserToAuthUserAdapter(loadUser(new AuthUserId(Long.valueOf(username))));
  }

  @Override
  public AuthUser loadUser(AuthUserId authId) {
    return mapper.authEntityToAuthUser(Optional.ofNullable(authUserEntityJpaRepository.findById(authId.getValue())).orElseThrow(
        ()-> new AuthUserDomainException("존재하지 않는 아이디입니다.")).get());
  }
}
