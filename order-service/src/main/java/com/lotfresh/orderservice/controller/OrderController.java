package com.lotfresh.orderservice.controller;

import com.lotfresh.orderservice.dto.request.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import com.lotfresh.orderservice.service.orchestrator.OrchestratorService;
import com.lotfresh.orderservice.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrchestratorService orchestratorService;

    @PostMapping
    public ResponseEntity insertOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        orchestratorService.orderTransaction(orderCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity changeStatus(@RequestBody OrderChangeStatusRequest orderChangeStatusRequest) {
        orderService.changeProductOrderStatus(orderChangeStatusRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refund")
    public ResponseEntity refundOrder(@RequestBody OrderRefundRequest orderRefundRequest){
        orchestratorService.refundTransaction(orderRefundRequest);
        return ResponseEntity.ok().build();
    }

}
