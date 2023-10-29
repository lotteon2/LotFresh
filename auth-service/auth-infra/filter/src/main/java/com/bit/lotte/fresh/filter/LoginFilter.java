package com.bit.lotte.fresh.filter;

import com.bit.lotte.fresh.auth.common.instant.LoginSessionTime;
import com.bit.lotte.fresh.auth.service.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.common.instant.RedirectInfo;
import com.bit.lotte.fresh.auth.common.instant.TokenName;
import com.bit.lotte.fresh.auth.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    public LoginFilter(AuthenticationManager authenticationManager) {
        setRequiresAuthenticationRequestMatcher(
            new AntPathRequestMatcher("/auth/login"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OauthLoginDomainCommand command = mapper.readValue(request.getInputStream(),
                OauthLoginDomainCommand.class);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                command.getAuthUserId(), null);

            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to parse login request", e);
        }
    }

    /**
     * @param authResult String customClaimValue = authResult.getAuthorities().stream().findFirst().get().getAuthority()
     *                   해당 코드에서 Optional API의 isPresent를 쓰지 않았는데 attemptAuthentication에서의 인증 성공이
     *                   suceessfulAuthentication의 chain으로 이어지니 체크하지 않아도 된다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain, Authentication authResult)
        throws ServletException, IOException {
        AuthUserId id = (AuthUserId) authResult.getPrincipal();
        response.setHeader("userId", id.getValue().toString());

        String roleClaimsKey = JwtTokenUtil.roleKeyName;
        String roleClaimsValue = authResult.getAuthorities().stream().findFirst().get()
            .getAuthority();

        String token = JwtTokenUtil.generateToken(id.getValue().toString(), roleClaimsKey, roleClaimsValue,
            new Date(System.currentTimeMillis()+ LoginSessionTime.LOGIN_SESSION_SEC* 1000L));

        response.setHeader(TokenName.AUTHENTICATION_TOKEN_NAME, TokenName.AUTHENTICATION_TOKEN_PREFIX + token);
        log.info("header is set: " + response.getHeader(TokenName.AUTHENTICATION_TOKEN_NAME) + " " + token);

    }


    /**
     * @param response 회원가입을 위해서 userId를 response에 전달
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) throws IOException {
        log.info("response header: " + response.getHeader("userId"));
        response.setStatus(RedirectInfo.REDIRECT_STATUS);
        response.encodeRedirectURL(RedirectInfo.DOMAIN + RedirectInfo.SIGN_UP_URI);

    }

}
