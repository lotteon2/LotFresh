package com.lotfresh.productservice.domain.product.feign;

import com.lotfresh.productservice.config.StorageFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "storage-service", configuration = StorageFeignConfig.class) // 임시 url
public interface StorageApiClient {

  @GetMapping("/storageproduct/stock/{province}/{productId}")
  Integer getStock(
      @RequestHeader(value = "userId") Long userId,
      @PathVariable("province") String province,
      @PathVariable("productId") Long productId);

  @GetMapping("/storages/sales-products/{productId}/stock")
  Integer getSalesProductStock(
      @RequestHeader(value = "userId", required = false) Long userId,
      @PathVariable("productId") Long productId);
}
