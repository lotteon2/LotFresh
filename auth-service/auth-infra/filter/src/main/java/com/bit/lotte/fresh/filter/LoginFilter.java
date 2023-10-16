package com.bit.lotte.fresh.filter;

import com.bit.lotte.fresh.auth.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.auth.dto.command.OauthLoginDomainCommand;
import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.common.instant.LoginSessionTime;
import com.bit.lotte.fresh.common.instant.SignUpRedirectInfo;
import com.bit.lotte.fresh.common.instant.TokenName;
import com.bit.lotte.fresh.common.util.CookieUtil;
import com.bit.lotte.fresh.common.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    public LoginFilter(AuthenticationManager authenticationManager) {
        setRequiresAuthenticationRequestMatcher(
            new AntPathRequestMatcher("/auth/login", "POST"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) {
        try {
            // Parse the login request body and extract username and password
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
        String id = authResult.getPrincipal().toString();
        String roleClaimsKey = JwtTokenUtil.roleKeyName;
        String roleClaimsValue = authResult.getAuthorities().stream().findFirst().get()
            .getAuthority();
        //지금 현재 ROLE_CATEGORY_ADMIN7 붙어있는 것
        String token = JwtTokenUtil.generateToken(id, roleClaimsKey, roleClaimsValue,
            new Date(LoginSessionTime.LOGIN_SESSION_SEC));




        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) {
        response.setStatus(SignUpRedirectInfo.REDIRECT_STATUS);
        response.encodeRedirectURL(SignUpRedirectInfo.URL + SignUpRedirectInfo.SIGN_UP);
    }

}
