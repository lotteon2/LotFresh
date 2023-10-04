package com.lotfresh.orderservice.aggregate.orchestrator.domain;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow.OrderWorkflow;
import com.lotfresh.orderservice.aggregate.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.aggregate.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.aggregate.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.orderStep.InventoryStep;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.orderStep.OrderStep;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.orderStep.PaymentStep;
import com.lotfresh.orderservice.aggregate.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkflowGenerator {
    private final OrderService orderService;
    private final InventoryFeignClient inventoryFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public OrderWorkflow generateOrderWorkflow(OrderCreateRequest orderCreateRequest){
        List<WorkflowStep> workflowSteps = List.of(new OrderStep(orderService, orderCreateRequest),
                new InventoryStep(inventoryFeignClient, orderCreateRequest.getProductRequests()),
                new PaymentStep(paymentFeignClient, orderCreateRequest.getUserId()));

        return OrderWorkflow.builder().workflowSteps(workflowSteps).build();
    }
}
