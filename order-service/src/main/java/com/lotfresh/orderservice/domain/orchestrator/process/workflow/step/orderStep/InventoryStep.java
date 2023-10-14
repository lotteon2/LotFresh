package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InventoryStep extends WorkflowStep {
    private final InventoryFeignClient feignClient;
    private final List<InventoryRequest> inventoryRequests;
    private final KafkaProducer kafkaProducer;


    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public void process() {
        feignClient.deductQuantity(inventoryRequests);
        changeStatus(WorkflowStepStatus.COMPLETE);
    }

    @Override
    public void revert() {
        kafkaProducer.send("inventory",inventoryRequests);
        changeStatus(WorkflowStepStatus.FAILED);
    }

    @Override
    public void changeStatus(WorkflowStepStatus status) {
        this.status = status;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return status;
    }
}
