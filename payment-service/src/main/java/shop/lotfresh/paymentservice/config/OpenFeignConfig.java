package shop.lotfresh.paymentservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "shop.lotfresh.paymentservice")
@Configuration
public class OpenFeignConfig {}
