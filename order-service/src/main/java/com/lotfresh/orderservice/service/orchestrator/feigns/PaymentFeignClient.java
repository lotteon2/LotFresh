package com.lotfresh.orderservice.service.orchestrator.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Payment",url="localhost:80/api/payments")
public interface PaymentFeignClient {
    @PostMapping("/kakaopay/request")
    ResponseEntity requestPayment(@RequestBody Long userId);
    @PostMapping("/kakaopay/revertRequest")
    ResponseEntity revertRequestPayment(@RequestBody Long userId);
}
