package com.lotfresh.orderservice.util;

import com.lotfresh.orderservice.domain.order.scheduler.BestSellerScheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class StartupRunner {

    @Bean
    public CommandLineRunner saveBestSellerProducts(BestSellerScheduler bestSellerScheduler) {
        return args -> {
            bestSellerScheduler.saveBestSellerProducts();
        };
    }
}
