package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public class NormalInventoryStep implements InventoryStep {
    private final String workflowName;
    private final InventoryFeignClient feignClient;
    private final List<InventoryRequest> inventoryRequests;
    private final KafkaProducer kafkaProducer;

    public WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public Object process() {
        ResponseEntity result = feignClient.deductNormalQuantity(inventoryRequests);
        changeStatus(WorkflowStepStatus.COMPLETE);
        return result;
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

    @Override
    public String getWorkflowName() {
        return workflowName;
    }

}
