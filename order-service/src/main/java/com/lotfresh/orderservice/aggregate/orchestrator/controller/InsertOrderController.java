package com.lotfresh.orderservice.aggregate.orchestrator.controller;

import com.lotfresh.orderservice.aggregate.orchestrator.service.OrchestratorService;
import com.lotfresh.orderservice.aggregate.orchestrator.controller.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class InsertOrderController {
    private final OrchestratorService orchestratorService;
    @PostMapping
    public ResponseEntity insertOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        orchestratorService.orderTransaction(orderCreateRequest);
        return ResponseEntity.ok().build();
    }
}
