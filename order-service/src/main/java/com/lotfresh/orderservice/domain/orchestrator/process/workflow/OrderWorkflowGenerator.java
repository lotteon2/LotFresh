package com.lotfresh.orderservice.domain.orchestrator.process.workflow;

import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep.InventoryStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep.PaymentStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderWorkflowGenerator {
    private final InventoryFeignClient inventoryFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final KafkaProducer kafkaProducer;

    public OrderWorkflow generateOrderWorkflow(List<InventoryRequest> inventoryRequests, PaymentRequest paymentRequest){
        List<WorkflowStep> workflowSteps = List.of(
                new InventoryStep(inventoryFeignClient,inventoryRequests,kafkaProducer),
                new PaymentStep(paymentFeignClient,paymentRequest,kafkaProducer));

        return OrderWorkflow.builder()
                .workflowSteps(workflowSteps)
                .build();
    }
}
