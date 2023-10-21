package com.bit.lotte.fresh.auth.dataaccess.adapter;

import com.bit.lotte.fresh.auth.service.SecurityUserLoadService;
import com.bit.lotte.fresh.auth.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.service.repository.AuthUserRepository;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServiceAdapter implements UserDetailsService,
    OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    SecurityUserLoadService {


  private final AuthUserRepository repository;
  private final AuthUserDataAccessMapper mapper;


  @Override
  public AuthUser loadAuthUser(AuthUserId authId) {
    return Optional.ofNullable(repository.getAuthUser(authId)).orElseThrow(
        () -> new AuthUserDomainException("존재하지 않는 아이디입니다."));
  }


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = service.loadUser(userRequest);
    Map<String, Object> originAttributes = oAuth2User.getAttributes();
    String registrationId = userRequest.getClientRegistration()
        .getRegistrationId();
    AuthUserAdaptor adaptor = (AuthUserAdaptor) loadUserByUsername(registrationId);

    return new OauthUserAdapter(adaptor.getAuthUser(),originAttributes);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return mapper.authUserToAuthUserAdapter(loadAuthUser(new AuthUserId(Long.valueOf(username))));
  }
}
