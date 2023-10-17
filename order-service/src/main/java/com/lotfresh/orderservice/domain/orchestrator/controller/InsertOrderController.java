package com.lotfresh.orderservice.domain.orchestrator.controller;

import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.service.OrchestratorService;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class InsertOrderController {
    private final OrchestratorService orchestratorService;
    private final KafkaProducer kafkaProducer;

    @PostMapping()
    public ResponseEntity createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        orchestratorService.createOrderAndRequestToPayment(orderCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/normal")
    public ResponseEntity insertNormalOrder(@RequestHeader(value = "userId", required = false) Long userId,
            @RequestParam Long orderId, @RequestParam Boolean isFromCart) {
        orchestratorService.orderNormalTransaction(userId, orderId, isFromCart);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/sales")
//    public ResponseEntity insertSalesOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
//        orchestratorService.orderSalesTransaction(orderCreateRequest);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/failed")
    public void kakaoFailed(@RequestParam Long orderId) {
        kafkaProducer.send("payment-abort",orderId, PaymentStatus.FAILED);
    }

    @GetMapping("/canceled")
    public void kakaoCanceled(@RequestParam Long orderId) {
        kafkaProducer.send("payment-abort",orderId,PaymentStatus.CANCELED);
    }
}
