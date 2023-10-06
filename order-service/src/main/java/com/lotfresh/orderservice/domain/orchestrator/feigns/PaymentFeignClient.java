package com.lotfresh.orderservice.domain.orchestrator.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="Payment",url="localhost:80/api/payments")
public interface PaymentFeignClient {
    @PostMapping("/kakaopay/request")
    ResponseEntity requestPayment();
    @PostMapping("/kakaopay/revertRequest")
    ResponseEntity revertRequestPayment();
}
