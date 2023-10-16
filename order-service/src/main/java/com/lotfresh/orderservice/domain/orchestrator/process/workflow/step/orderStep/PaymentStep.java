package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentStep extends WorkflowStep {
    private final PaymentFeignClient feignClient;
    private final PaymentRequest paymentRequest;
    private final KafkaProducer kafkaProducer;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public void process() {
        feignClient.requestPayment(paymentRequest);
        changeStatus(WorkflowStepStatus.COMPLETE);
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
}