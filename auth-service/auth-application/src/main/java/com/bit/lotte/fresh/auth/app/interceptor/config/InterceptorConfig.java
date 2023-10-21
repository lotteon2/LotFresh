package com.bit.lotte.fresh.auth.app.interceptor.config;

import com.bit.lotte.fresh.auth.app.interceptor.CategoryAdminSubIdListInitHandler;
import com.bit.lotte.fresh.auth.service.AuthUserCommandHandler;
import com.bit.lotte.fresh.feign.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
  private final AuthUserCommandHandler commandHandler;
  private final ProductFeignClient productFeignClient;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new CategoryAdminSubIdListInitHandler(productFeignClient,commandHandler))
        .addPathPatterns("/auth/admins/target/{targetId}/category/{categoryId}");

  }

}
