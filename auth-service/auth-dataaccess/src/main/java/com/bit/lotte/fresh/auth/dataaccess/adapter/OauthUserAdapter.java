package com.bit.lotte.fresh.auth.dataaccess.adapter;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
@AllArgsConstructor
@RequiredArgsConstructor
public class OauthUserAdapter implements OAuth2User {

  private AuthUser authUser;
  private Map<String, Object> attributes;




  public Map<String, Object> getAttributes() {
    return this.attributes;
  }


  public Collection<? extends GrantedAuthority> getAuthorities() {
    GrantedAuthority authority = null;
    if (authUser.getUserRole().equals(AuthRole.ROLE_CATEGORY_ADMIN)) {
      authority = new SimpleGrantedAuthority(
          AuthRole.ROLE_CATEGORY_ADMIN + authUser.getDescription());
    } else {
      authority = new SimpleGrantedAuthority(authUser.getUserRole().name());
    }
    return List.of(authority);
  }

  public String getName() {
    return authUser.getId().getValue().toString();
  }

  public String getEmail() {
    return authUser.getEmail();
  }

}
