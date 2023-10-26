package com.bit.lotte.fresh.config.oauth;

import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.common.instant.RedirectInfo;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

      AuthUserId id = new AuthUserId(Long.valueOf(authentication.getPrincipal().toString()));

      request.setAttribute("AuthUserLoginCommand", new LoginAuthDomainCommand(id, AuthProvider.valueOf(request.getParameter("authProvider"))));
      request.getRequestDispatcher(RedirectInfo.DOMAIN+RedirectInfo.LOGIN_URI).forward(request, response);
    }
  }

