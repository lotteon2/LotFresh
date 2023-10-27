package com.bit.lotte.fresh.auth.dataaccess.adapter;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@RequiredArgsConstructor
public class AuthUserAdaptor implements UserDetails {

    private final AuthUser authUser;


  @Override
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

    @Override
    public String getPassword() {
      return authUser.getPassword();
    }

    @Override
    public String getUsername() {
      return authUser.getEntityId().toString();
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
      return !authUser.getUserRole().equals(AuthRole.ROLE_NOT_VERIFIED);
    }

}


