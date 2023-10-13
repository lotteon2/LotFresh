package com.lotfresh.productservice.util;

import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupRunner {

  @Bean
  public CommandLineRunner updateExpiredDiscounts(DiscountRepository discountRepository) throws Exception {
    return args -> {
      discountRepository.updateExpiredDiscounts();
    };
  }
}
