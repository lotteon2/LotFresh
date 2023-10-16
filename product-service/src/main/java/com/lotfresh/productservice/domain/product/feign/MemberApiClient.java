package com.lotfresh.productservice.domain.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "member-service", url = "http://localhost:8001") // 임시 url
public interface MemberApiClient {

  @GetMapping("/members/{memberId}/memberAddress")
  String getMemberAddress(@PathVariable("memberId") Long memberId);
}
