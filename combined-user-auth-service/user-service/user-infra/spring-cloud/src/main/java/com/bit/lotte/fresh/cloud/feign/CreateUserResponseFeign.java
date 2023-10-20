package com.bit.lotte.fresh.cloud.feign;

import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "auth-service",  url = "${spring.cloud.feign.auth.uri}")
public interface CreateUserResponseFeign {
  @PostMapping("/")
  ResponseEntity<Object> responseToAuthService(AuthUserId authId);
}
