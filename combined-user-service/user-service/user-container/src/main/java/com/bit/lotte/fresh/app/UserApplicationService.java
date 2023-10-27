package com.bit.lotte.fresh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = {"com.bit.lotte.fresh.cloud.feign"})
@EnableJpaRepositories(basePackages = {"com.bit.lotte.fresh.data.user.repository"})
@EntityScan(basePackages = {"com.bit.lotte.fresh.data.user.entity"})
@SpringBootApplication(scanBasePackages = "com.bit.lotte.fresh.service")
@ComponentScan(basePackages = "com.bit.lotte.fresh.service.repository")
@ComponentScan(basePackages = "com.bit.lotte.fresh.app")
@ComponentScan(basePackages = "com.bit.lotte.fresh.data.user.adapter")
@ComponentScan(basePackages = "com.bit.lotte.fresh.data.user.mapper")
@ComponentScan(basePackages = "com.bit.lotte.fresh.message.publish")
@EnableDiscoveryClient
public class UserApplicationService {
// 
  public static void main(String[] args) {
    SpringApplication.run(UserApplicationService.class, args);

  }
}
