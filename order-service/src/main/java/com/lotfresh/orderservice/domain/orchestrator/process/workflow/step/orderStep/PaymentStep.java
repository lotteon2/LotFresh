package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class PaymentStep implements WorkflowStep {
    private final String workflowName;
    private final PaymentFeignClient feignClient;
    private final PaymentRequest paymentRequest;
    private final KafkaProducer kafkaProducer;

    public WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public Object process() {
        ResponseEntity result = feignClient.requestPayment(paymentRequest);
        changeStatus(WorkflowStepStatus.COMPLETE);
        return result;
    }

    @Override
    public void revert() {
        kafkaProducer.send("payment",paymentRequest);
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
