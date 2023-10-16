package com.bit.lotte.fresh.auth.interceptor;

import com.bit.lotte.fresh.auth.valueobject.AuthRole;
import com.bit.lotte.fresh.common.instant.LoginSessionTime;
import com.bit.lotte.fresh.common.instant.TokenName;
import com.bit.lotte.fresh.common.util.CookieUtil;
import com.bit.lotte.fresh.common.util.JwtTokenUtil;
import com.bit.lotte.fresh.feign.ProductFeignClient;
import com.bit.lotte.fresh.feign.dto.SubCategoryAdminIdListDto;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class CategoryAdminSubIdListHandler implements HandlerInterceptor {

    private final ProductFeignClient productFeignClient;

    /**
     * ResponseEntity의 반환형의 suppresWarining Response Body의 객체가 하나이므로 안전하게 형 변환이 가능
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestUri = request.getRequestURI();
        String token = CookieUtil.getCookie(request, TokenName.JWT_COOKIE_NAME);

        // Check if the URI is "/auth/login" and the user has "ROLE_CATEGORY_ADMIN"
        if (requestUri.equals("/auth/login") && AuthRole.valueOf(JwtTokenUtil.getRoleString(token)).equals(AuthRole.ROLE_CATEGORY_ADMIN)) {
            if (!JwtTokenUtil.checkCategoryAdminHasSubIdList(token)) {
                ResponseEntity<SubCategoryAdminIdListDto> responseEntity = productFeignClient.requestSubAdminIdList(
                        Long.valueOf(JwtTokenUtil.parseCategoryAdminCategoryId(token)));
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    Set<Long> idSet = (Set<Long>) responseEntity.getBody();
                    String idString = longSetToString(idSet);
                    String updatedToken = JwtTokenUtil.setCategoryAdminSubIdList(token, idString);
                    CookieUtil.setCookie(response, TokenName.JWT_COOKIE_NAME, TokenName.PREFIX_TOKEN + updatedToken,
                            LoginSessionTime.LOGIN_SESSION_SEC);
                }
            }
        }

        // Continue with the request
        return true;
    }

    private String longSetToString(Set<Long> set) {
        return set.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}







