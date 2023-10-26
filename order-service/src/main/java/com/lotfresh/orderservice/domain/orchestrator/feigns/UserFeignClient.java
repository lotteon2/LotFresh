package com.lotfresh.orderservice.domain.orchestrator.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="User",url="localhost:8081/api/user")
public interface UserFeignClient {
    @GetMapping("/{userId}")
    String getProvince(@PathVariable("userId") Long userId);
}
