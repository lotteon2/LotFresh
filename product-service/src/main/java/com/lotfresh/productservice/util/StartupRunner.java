package com.lotfresh.productservice.util;

import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.product.scheduler.ProductScheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class StartupRunner {

  @Bean
  public CommandLineRunner updateExpiredDiscounts(DiscountRepository discountRepository, ProductScheduler productScheduler)
      throws Exception {
    return args -> {
      discountRepository.updateExpiredDiscounts();
      productScheduler.clearCache();
    };
  }
}
