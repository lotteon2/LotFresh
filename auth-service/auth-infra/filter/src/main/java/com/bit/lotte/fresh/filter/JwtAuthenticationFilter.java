package com.bit.lotte.fresh.filter;

import com.bit.lotte.fresh.auth.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.auth.common.util.RedisTokenBlacklistUtil;
import com.bit.lotte.fresh.auth.common.util.TokenParserUtil;
import com.bit.lotte.fresh.user.common.instant.AuthHeaderName;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final RedisTokenBlacklistUtil redisTokenBlacklistUtil;


  /**
   * 로그아웃시에 Redis에 BlackList에 추가되어 해당 토큰은 더 이상 사용하지 못 하게 됩니다. 해당 Filter는 Authentication을 다룹니다. 요구사항의
   * 하위 카테고리의 관리자를 다루는 구체적 요구사항은 Filter를 거친 이후 interceptor에서 다룹니다. 해당 필터에서는 로그인할 때 데이터베이스에서 로드한 회원
   * 정보를 담습니다. 1번 째는 유저 아이디, 2번 째는 회원 Role입니다.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = TokenParserUtil.extractToken(request);
      if (token != null) {
        if (!redisTokenBlacklistUtil.isTokenBlacklisted(token)) {
          JwtTokenUtil.verifyToken(token);
          String id = JwtTokenUtil.getSubjectFromToken(token);
          String roleString = JwtTokenUtil.getRoleString(token);
          SecurityContextHolder.getContext().setAuthentication(
              new UsernamePasswordAuthenticationToken(new AuthUserId(Long.valueOf(id)), null,
                  List.of(new SimpleGrantedAuthority(roleString))));
          response.addHeader(AuthHeaderName.REQUEST_ID_HEADER, id);
        } else {
          throw new BadCredentialsException("로그아웃이 처리된 토큰은 사용할 수 없습니다.");
        }
      }
    } catch (SignatureException | MalformedJwtException exception) {
      response.setStatus(401);
      throw new JwtException(exception.getMessage());
    }
      filterChain.doFilter(request, response);


  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestURI = request.getRequestURI();
    String requestMethod = request.getMethod();
    return requestURI.matches("/auth/login") || (requestMethod.equals("POST") && requestURI.equals(
        "/auth"));

  }
}
