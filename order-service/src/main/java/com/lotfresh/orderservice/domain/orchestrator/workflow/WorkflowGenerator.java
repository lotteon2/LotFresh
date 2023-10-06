package com.lotfresh.orderservice.domain.orchestrator.workflow;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.step.orderStep.InventoryStep;
import com.lotfresh.orderservice.domain.orchestrator.step.orderStep.OrderStep;
import com.lotfresh.orderservice.domain.orchestrator.step.orderStep.PaymentStep;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkflowGenerator {
    private final OrderService orderService;
    private final InventoryFeignClient inventoryFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public OrderWorkflow generateOrderWorkflow(OrderCreateRequest orderCreateRequest){
        List<InventoryRequest> inventoryRequests = orderCreateRequest.getProductRequests().stream()
                .map(ProductRequest::toInventoryRequest)
                .collect(Collectors.toList());

        List<WorkflowStep> workflowSteps = List.of(new OrderStep(orderService,orderCreateRequest.getProductRequests()),
                new InventoryStep(inventoryFeignClient,inventoryRequests),
                new PaymentStep(paymentFeignClient));

        return OrderWorkflow.builder().workflowSteps(workflowSteps).build();
    }
}
