package com.lotfresh.productservice.domain.product.feign;

import com.lotfresh.productservice.config.StorageFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    value = "storage-service",
    url = "http://localhost:8006",
    configuration = StorageFeignConfig.class) // 임시 url
public interface StorageApiClient {

  @GetMapping("/storages/products/{productId}/stock")
  Integer getStock(
      @RequestHeader(value = "userId", required = false) Long userId,
      @PathVariable("productId") Long productId);
}
