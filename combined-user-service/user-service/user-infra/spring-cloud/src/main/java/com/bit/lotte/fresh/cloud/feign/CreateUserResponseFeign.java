package com.bit.lotte.fresh.cloud.feign;

import com.bit.lotte.fresh.user.common.message.CreateAuthUserCommand;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "auth-service",  url = "localhost:8082")
public interface CreateUserResponseFeign {
  @PostMapping("/auth")
  ResponseEntity<Object> responseToAuthService(CreateAuthUserCommand command);
}
