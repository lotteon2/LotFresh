package com.lotfresh.orderservice.service.orchestrator.steps;

import com.lotfresh.orderservice.domain.orchestrator.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStepStatus;
import com.lotfresh.orderservice.service.orchestrator.feigns.PaymentFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentStep implements WorkflowStep {
    private final PaymentFeignClient feignClient;
    private final Long userId;
    private WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public WorkflowStepStatus getStatus() {
        return status;
    }

    @Override
    public void process() {
        feignClient.requestPayment(userId);
    }

    @Override
    public void revert() {
        feignClient.revertRequestPayment(userId);
    }

    @Override
    public void changeStatus(WorkflowStepStatus status) {
        this.status = status;
    }
}
