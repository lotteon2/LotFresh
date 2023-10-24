package com.lotfresh.orderservice.domain.orchestrator.controller;

import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.service.OrchestratorService;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class InsertOrderController {
    private final OrchestratorService orchestratorService;
    private final KafkaProducer kafkaProducer;

    @PostMapping()
    public ResponseEntity createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity.ok().body(orchestratorService.createOrderAndRequestToPayment(orderCreateRequest));
    }

    @GetMapping("/normal")
    public ResponseEntity insertNormalOrder(@RequestHeader(value = "userId", required = false) Long userId,
            @RequestParam Long orderId, @RequestParam Boolean isFromCart) {
        String userProvince = "temporal";
        orchestratorService.orderNormalTransaction(userId, userProvince, orderId, isFromCart);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sales")
    public ResponseEntity insertSalesOrder(@RequestHeader(value = "userId", required = false) Long userId,
                                           @RequestParam Long orderId, @RequestParam Boolean isFromCart) {
        String userProvince = "temporal";
        orchestratorService.orderSalesTransaction(userId, userProvince, orderId, isFromCart);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/failed")
    public void kakaoFailed(@RequestParam Long orderId) {
        kafkaProducer.send("payment-abort",orderId, PaymentStatus.FAILED);
    }

    @GetMapping("/canceled")
    public void kakaoCanceled(@RequestParam Long orderId) {
        kafkaProducer.send("payment-abort",orderId,PaymentStatus.CANCELED);
    }
}
