package com.bit.lotte.fresh.config;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.auth.service.repository.AuthUserRepository;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.auth.dataaccess.adapter.UserDetailServiceAdapter;
import com.bit.lotte.fresh.auth.dataaccess.mapper.AuthUserDataAccessMapper;
import com.bit.lotte.fresh.config.redis.RedisConfiguration;
import com.bit.lotte.fresh.filter.ExceptionHandlerFilter;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthUserRepository repository;
  private final RedisConfiguration redisConfiguration;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeHttpRequests().antMatchers("login").permitAll()
        .antMatchers("/auth").permitAll();

    return http.addFilterAt(loginFilter(customManger()), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
            exceptionHandlerFilter(), LogoutFilter.class)
        .build();
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
    return new UserDetailServiceAdapter(repository,
        new AuthUserDataAccessMapper());
  }

  @Bean
  public LoginFilter loginFilter(
      AuthenticationManager authenticationManager) {
    return new LoginFilter(customManger());
  }


  @Bean
  public ExceptionHandlerFilter exceptionHandlerFilter() {
    return new ExceptionHandlerFilter();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(redisConfiguration.redisTokenBlacklistUtil());
  }


}
