package com.bit.lotte.fresh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.bit.lotte.fresh.data.user.repository"})
@EntityScan(basePackages = {"com.bit.lotte.fresh.data.user.entity"})
@SpringBootApplication(scanBasePackages = "com.bit.lotte.fresh.app")
@EnableFeignClients
@ComponentScan(basePackages = {"com.bit.lotte.fresh"})
public class UserApplicationService {

  public static void main(String[] args) {
    SpringApplication.run(UserApplicationService.class, args);
  }
}
