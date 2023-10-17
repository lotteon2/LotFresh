package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.KakaopayReadyRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="Payment",url="localhost:80/api/payments")
public interface PaymentFeignClient {
    @PostMapping("/payments/kakaopay/ready")
    ResponseEntity<String> kakaopayReady(KakaopayReadyRequest kakaopayReadyRequest);
    @PostMapping("/kakaopay/request")
    ResponseEntity requestPayment(PaymentRequest paymentRequest);
    @PostMapping("/kakaopay/revertRequest")
    ResponseEntity revertRequestPayment();
}
