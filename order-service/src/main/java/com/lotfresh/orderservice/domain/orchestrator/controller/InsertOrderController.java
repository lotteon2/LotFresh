package com.lotfresh.orderservice.domain.orchestrator.controller;

import com.lotfresh.orderservice.domain.orchestrator.service.OrchestratorService;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class InsertOrderController {
    private final OrchestratorService orchestratorService;
    @PostMapping("/normal")
    public ResponseEntity insertNormalOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        orchestratorService.orderNormalTransaction(orderCreateRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/sales")
    public ResponseEntity insertSalesOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        orchestratorService.orderSalesTransaction(orderCreateRequest);
        return ResponseEntity.ok().build();
    }
}
