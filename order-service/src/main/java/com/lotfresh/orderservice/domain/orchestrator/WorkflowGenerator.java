package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.service.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.service.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.service.orchestrator.steps.order.InventoryStep;
import com.lotfresh.orderservice.service.orchestrator.steps.order.OrderStep;
import com.lotfresh.orderservice.service.orchestrator.steps.order.PaymentStep;
import com.lotfresh.orderservice.service.order.OrderService;
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
