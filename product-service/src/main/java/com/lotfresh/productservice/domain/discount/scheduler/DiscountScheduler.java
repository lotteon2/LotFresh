package com.lotfresh.productservice.domain.discount.scheduler;

import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiscountScheduler {
  private final DiscountRepository discountRepository;

  @Transactional
  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  public void updateExpiredDiscounts() {
    discountRepository.updateExpiredDiscounts();
  }
}
