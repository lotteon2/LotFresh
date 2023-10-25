package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.exception.SagaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
@RequiredArgsConstructor
public class SalesInventoryStep implements InventoryStep{
    private final String workflowName;
    private final InventoryFeignClient feignClient;
    private final InventoryRequest inventoryRequest;
    private final KafkaProducer kafkaProducer;

    public WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public Object process() {
        try {
            ResponseEntity result = feignClient.deductSalesStock(inventoryRequest);
            changeStatus(WorkflowStepStatus.COMPLETE);
            log.info("SalesInventoryStep : 성공");
            return result;
        } catch (Exception e) {
            log.error("SalesInventoryStep : 실패");
            throw new SagaException(e.getMessage(),"InventoryStep");
        }
    }

    @Override
    public void revert() {
        kafkaProducer.send("inventory", inventoryRequest);
        changeStatus(WorkflowStepStatus.FAILED);
        log.info("SalesInventoryStepRevert : 성공");
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
