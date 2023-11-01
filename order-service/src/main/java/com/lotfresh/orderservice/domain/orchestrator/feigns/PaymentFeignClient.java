package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.KakaopayReadyRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="payment-service")
public interface PaymentFeignClient {
    @PostMapping("/payments/kakaopay/ready")
    ResponseEntity<String> kakaopayReady(KakaopayReadyRequest kakaopayReadyRequest,
                                         @RequestHeader(value = "userId") Long userId);
    @PostMapping("/payments/kakaopay/approve")
    ResponseEntity requestPayment(PaymentRequest paymentRequest);

}
