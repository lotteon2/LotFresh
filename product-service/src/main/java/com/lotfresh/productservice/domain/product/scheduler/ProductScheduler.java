package com.lotfresh.productservice.domain.product.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScheduler {

  private final CacheManager cacheManager;

  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  public void clearCache() {
    cacheManager.getCache("newProductsCache").clear();
  }
}
