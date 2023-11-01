package com.lotfresh.orderservice.domain.orchestrator.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserFeignClient {
    @GetMapping("/users/{userId}/default")
    String getProvince(@PathVariable("userId") Long userId);
}
