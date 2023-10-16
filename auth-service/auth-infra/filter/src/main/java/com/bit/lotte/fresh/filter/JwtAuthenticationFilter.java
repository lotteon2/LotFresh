package com.bit.lotte.fresh.filter;

import com.bit.lotte.fresh.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
      String token = extractToken(request);
      if (token != null) {
        if (JwtTokenUtil.verifyToken(token)) {
          String id = JwtTokenUtil.getSubjectFromToken(token);
          String roleString = JwtTokenUtil.getRoleString(token);
          SecurityContextHolder.getContext().setAuthentication(
              new UsernamePasswordAuthenticationToken(new AuthUserId(Long.valueOf(id)), null,
                  List.of(new SimpleGrantedAuthority(roleString))));
        } else {
          throw new BadCredentialsException("토큰이 일치하지않습니다.");
        }
      }
    filterChain.doFilter(request, response);
  }


  private String extractToken(HttpServletRequest request) {

    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }
}
