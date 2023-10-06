package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.orchestrator.workflow.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.workflow.WorkflowGenerator;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final WorkflowGenerator workflowGenerator;
    private final OrderService orderService;

    public Orchestrator orderTransaction(OrderCreateRequest orderCreateRequest) {
        OrderCreateResponse orderCreateResponse = orderService.insertOrder(orderCreateRequest.getProductRequests());
        List<InventoryRequest> inventoryRequests = orderCreateResponse.getOrderDetails().stream()
                .map(InventoryRequest::new)
                .collect(Collectors.toList());
        PaymentRequest paymentRequest = new PaymentRequest();

        Workflow orderWorkflow = workflowGenerator.generateOrderWorkflow(inventoryRequests,paymentRequest);
        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .build();

        try {
            orderOrchestrator.doTransaction();
        }catch(Exception e) {
            orderService.revertInsertOrder(orderCreateResponse);
            orderOrchestrator.revertProcess();
        }

        return orderOrchestrator;
    }
}
