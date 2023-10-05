package com.lotfresh.productservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.lotfresh.productservice")
@Configuration
public class OpenFeignConfig {}
