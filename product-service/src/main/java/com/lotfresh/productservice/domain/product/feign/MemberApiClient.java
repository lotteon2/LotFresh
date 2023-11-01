package com.lotfresh.productservice.domain.product.feign;

import com.lotfresh.productservice.config.MemberFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = MemberFeignConfig.class) // 임시 url
public interface MemberApiClient {

  @GetMapping("/members/{memberId}/memberAddress")
  String getMemberAddress(@PathVariable("memberId") Long memberId);
}
