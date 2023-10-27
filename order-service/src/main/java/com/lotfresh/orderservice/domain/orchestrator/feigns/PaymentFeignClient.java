package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.KakaopayReadyRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="Payment",url="payment-service:8085/payments")
public interface PaymentFeignClient {
    @PostMapping("/kakaopay/ready")
    ResponseEntity<String> kakaopayReady(KakaopayReadyRequest kakaopayReadyRequest);
    @PostMapping("/kakaopay/approve")
    ResponseEntity requestPayment(PaymentRequest paymentRequest);

}
