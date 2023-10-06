package com.lotfresh.orderservice.domain.orchestrator.workflow;

import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.step.orderStep.InventoryStep;
import com.lotfresh.orderservice.domain.orchestrator.step.orderStep.PaymentStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkflowGenerator {
    private final InventoryFeignClient inventoryFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public OrderWorkflow generateOrderWorkflow(List<InventoryRequest> inventoryRequests, PaymentRequest paymentRequest){
        List<WorkflowStep> workflowSteps = List.of(
                new InventoryStep(inventoryFeignClient,inventoryRequests),
                new PaymentStep(paymentFeignClient,paymentRequest));

        return OrderWorkflow.builder().workflowSteps(workflowSteps).build();
    }
}
