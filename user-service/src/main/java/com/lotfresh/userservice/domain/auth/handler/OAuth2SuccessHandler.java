package com.lotfresh.userservice.domain.auth.handler;

import com.lotfresh.userservice.domain.address.repository.AddressRepository;
import com.lotfresh.userservice.domain.auth.utils.TokenManager;
import com.lotfresh.userservice.domain.member.entity.Member;
import com.lotfresh.userservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final TokenManager tokenManager;
  private final MemberRepository memberRepository;
  private final AddressRepository addressRepository;

  @Value("${LOGIN_SUCCESS_URL}")
  private String loginSuccessUrl;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    var attributes = oAuth2User.getAttributes();

    Member member = memberRepository.findByEmail((String) attributes.get("email")).get();

    if (member == null) {
      member =
          Member.builder()
              .email((String) attributes.get("email"))
              .nickname((String) attributes.get("nickname"))
              .build();
      memberRepository.save(member);
    }

    String accessToken = tokenManager.generateToken(member.getId(), "USER");

    // 여기 리프레시 토큰 나중에 넣을듯?

    // 성공하면 리다이렉트로 돌리고, query 에서 accessToken 가져와서 셋팅할거임
    response.sendRedirect(loginSuccessUrl + accessToken);
  }
}
