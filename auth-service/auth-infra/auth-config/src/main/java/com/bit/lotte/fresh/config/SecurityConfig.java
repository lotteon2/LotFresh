package com.bit.lotte.fresh.config;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.repository.AuthUserRepository;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.dataaccess.adapter.UserDetailServiceAdapter;
import com.bit.lotte.fresh.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.dataaccess.repository.AuthUserEntityJpaRepository;
import com.bit.lotte.fresh.filter.JwtAuthenticationFilter;
import com.bit.lotte.fresh.filter.LoginFilter;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthUserRepository repository;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.
        authorizeHttpRequests().antMatchers("/api/product/").permitAll().
        antMatchers("/api/auth/login").permitAll().anyRequest().authenticated();
    http.addFilterAfter(loginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).build();
    return http.build();
  }

  @Bean
  AuthenticationManager customManger() {
    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {
        AuthUserId authUserId = (AuthUserId) authentication.getPrincipal();
        AuthUser authUser = repository.getAuthUser(authUserId);

        if (authUser != null) {
          GrantedAuthority authority = null;
          if (authUser.getUserRole().equals(AuthRole.ROLE_CATEGORY_ADMIN)) {
            authority = new SimpleGrantedAuthority(
                authUser.getUserRole().name() +authUser.getDescription());
          }
          //여기서 수정해주자 suffix 추가하자
          authority = new SimpleGrantedAuthority(authUser.getUserRole().name());
          return new UsernamePasswordAuthenticationToken(authUserId, null, List.of(authority));
        } else {

          throw new BadCredentialsException("존재하지 않는 유저 아이디입니다.");
        }
      }
    };
  }


  @Bean
  public UserDetailsService oauthUserDetail() {
    return new UserDetailServiceAdapter((AuthUserEntityJpaRepository) repository,
        new AuthUserDataAccessMapper());
  }

  @Bean
  public LoginFilter loginFilter() {
    return new LoginFilter(customManger());
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

}
