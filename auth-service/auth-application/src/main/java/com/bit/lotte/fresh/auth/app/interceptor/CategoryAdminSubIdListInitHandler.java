package com.bit.lotte.fresh.auth.app.interceptor;

import com.bit.lotte.fresh.auth.common.util.TokenParserUtil;
import com.bit.lotte.fresh.auth.exception.AuthUserDomainException;
import com.bit.lotte.fresh.auth.common.instant.TokenName;
import com.bit.lotte.fresh.auth.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.auth.service.AuthUserCommandHandler;
import com.bit.lotte.fresh.auth.service.dto.command.AuthUserIdCommand;
import com.bit.lotte.fresh.feign.ProductFeignClient;
import com.bit.lotte.fresh.feign.dto.SubCategoryAdminIdListDto;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class CategoryAdminSubIdListInitHandler implements HandlerInterceptor
{

    private final ProductFeignClient productFeignClient;
    private final AuthUserCommandHandler commandHandler;

    /**
     * ResponseEntity의 반환형의 suppresWarining Response Body의 객체가 하나이므로 안전하게 형 변환이 가능 해당 Handler는 카테고리
     * 관리자를 생성할 때 하위 관리자의 정보를 업데이트하기 위해서 만들어진 Handler입니다. update the target user description
     */
    @SuppressWarnings("unchecked")

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler)
        throws Exception {
        AuthUserId authUserId = (AuthUserId) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        String categoryId = request.getParameter("categoryId");
        String token = TokenParserUtil.extractToken(request);
        ResponseEntity<SubCategoryAdminIdListDto> responseEntity = productFeignClient.requestSubAdminIdList(
            Long.valueOf(categoryId));
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Set<Long> idSet = (Set<Long>) responseEntity.getBody();
            String idString = longSetToString(idSet);
            String updatedToken = JwtTokenUtil.setCategoryAdminSubIdList(token, idString);
            response.setHeader(TokenName.AUTHENTICATION_TOKEN_NAME,
                TokenName.AUTHENTICATION_TOKEN_NAME + updatedToken);
            log.info("ordinal token: " + token);
            log.info("updated token: " + updatedToken);
            commandHandler.setCategorySubIdDescription(new AuthUserIdCommand(authUserId),idString);
        } else {
            throw new AuthUserDomainException("카테고리 하위 관리자를 얻어올 수 없습니다");
        }
        return true;
    }

    private String longSetToString(Set<Long> set) {
        return set.stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));
    }
}







