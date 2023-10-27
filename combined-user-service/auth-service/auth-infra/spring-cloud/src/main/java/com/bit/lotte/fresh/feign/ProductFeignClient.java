package com.bit.lotte.fresh.feign;

import com.bit.lotte.fresh.feign.dto.SubCategoryAdminIdListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "provider", url = "https://www.lot-fresh.shop")
public interface ProductFeignClient {
  @GetMapping("/category-service/categories/{categoryId}/children")
  ResponseEntity<SubCategoryAdminIdListDto> requestSubAdminIdList(@PathVariable Long categoryId);
}
