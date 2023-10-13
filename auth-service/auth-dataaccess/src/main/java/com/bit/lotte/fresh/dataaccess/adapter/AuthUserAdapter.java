package com.bit.lotte.fresh.dataaccess.adapter;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserAdapter implements UserDetails {

 private AuthUser authUser;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
   if(authUser.getUserRole().equals(AuthRole.CATEGORY_ADMIN)){
     new SimpleGrantedAuthority();
   }
  }

  @Override
  public String getPassword() {
    return authUser.getPassword();
  }

  @Override
  public String getUsername() {
    return authUser.getId().toString();
  }

  @Override
  public boolean isAccountNonExpired() {
    return !authUser.isLoginSessionExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !authUser.getUserRole().equals(AuthRole.NOT_VERIFIED);
  }
}
