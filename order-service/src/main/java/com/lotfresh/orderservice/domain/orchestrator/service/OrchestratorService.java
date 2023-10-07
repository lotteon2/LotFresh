package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.orchestrator.workflow.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.workflow.OrderWorkflowGenerator;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderWorkflowGenerator orderWorkflowGenerator;
    private final OrderService orderService;

    public Orchestrator orderTransaction(OrderCreateRequest orderCreateRequest) {
        OrderCreateResponse orderCreateResponse = createOrder(orderCreateRequest);
        List<InventoryRequest> inventoryRequests = makeInventoryRequests(orderCreateResponse.getOrderDetails());
        PaymentRequest paymentRequest = makePaymentRequest();

        Workflow orderWorkflow = orderWorkflowGenerator.generateOrderWorkflow(inventoryRequests,paymentRequest);
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

    private OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest) {
        return orderService.insertOrder(orderCreateRequest.getProductRequests());
    }
    private List<InventoryRequest> makeInventoryRequests(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(InventoryRequest::new)
                .collect(Collectors.toList());
    }
    private PaymentRequest makePaymentRequest() {
        return new PaymentRequest();
    }



}
