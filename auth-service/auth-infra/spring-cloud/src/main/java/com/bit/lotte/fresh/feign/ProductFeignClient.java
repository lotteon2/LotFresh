package com.bit.lotte.fresh.feign;

import com.bit.lotte.fresh.feign.dto.SubCategoryAdminIdListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "provider", url = "http://localhost:8083/product/category/sub/id")
public interface ProductFeignClient {
  @GetMapping("/auth/category/{categoryId}")
  ResponseEntity<SubCategoryAdminIdListDto> requestSubAdminIdList(@PathVariable Long categoryId);
}
