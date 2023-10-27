package com.lotfresh.orderservice.domain.orchestrator.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="User",url="user-service:8081/users")
public interface UserFeignClient {
    @GetMapping("/{userId}/default")
    String getProvince(@PathVariable("userId") Long userId);
}
