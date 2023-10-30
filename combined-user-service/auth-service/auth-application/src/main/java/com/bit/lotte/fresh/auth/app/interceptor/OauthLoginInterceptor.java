package com.bit.lotte.fresh.auth.app.interceptor;

import com.bit.lotte.fresh.auth.common.instant.RedirectInfo;
import com.bit.lotte.fresh.auth.service.dto.command.LoginAuthDomainCommand;
import com.bit.lotte.fresh.auth.service.dto.response.LoginAuthUserResponse;
import com.bit.lotte.fresh.auth.valueobject.Secret;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public class OauthLoginInterceptor implements HandlerInterceptor {

	private final RestTemplate restTemplate;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		AuthUserId authUserId = (AuthUserId) request.getAttribute("id");
		AuthProvider provider = (AuthProvider) request.getAttribute("provider");
		LoginAuthDomainCommand command = new LoginAuthDomainCommand(authUserId,provider);

		HttpEntity<LoginAuthDomainCommand> requestEntity = new HttpEntity<>(command);
		ResponseEntity<LoginAuthUserResponse> responseEntity = restTemplate.postForEntity(
				Secret.LOGIN_REQUEST_URI,
				requestEntity,
				LoginAuthUserResponse.class
		);
		/**
		 * getHeaders에서 NullPointException이 발생하지 않는 이유는 statusCode가 301이 아니라면 JwtToken이 발행되어
		 * Authoirzation Header가 존재한다.
		 */
		if(!responseEntity.getStatusCode().is3xxRedirection()){
				String token = requestEntity.getHeaders().get("Authorization").get(0);
				response.addHeader("Authorization", token);
				response.setStatus(200);
		}
		else{
			response.setStatus(301);
			response.sendRedirect(RedirectInfo.DOMAIN+ RedirectInfo.SIGN_UP_URI);
		}



	}
}
