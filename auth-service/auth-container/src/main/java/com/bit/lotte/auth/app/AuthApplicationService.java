package com.bit.lotte.auth.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients({"com.bit.lotte.fresh.feign"})
@EnableJpaRepositories(basePackages = {"com.bit.lotte.fresh.auth.dataaccess.repository"})
@EntityScan(basePackages = {"com.bit.lotte.fresh.auth.dataaccess.entity"})
@SpringBootApplication(scanBasePackages = "com.bit.lotte.auth.app")
@ComponentScan("com.bit.lotte.fresh.config")
@ComponentScan("com.bit.lotte.fresh.auth.app")
@ComponentScan("com.bit.lotte.fresh.auth.dataaccess")
@ComponentScan("com.bit.lotte.fresh.auth.service")
public class AuthApplicationService {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplicationService.class, args);

  }
}
