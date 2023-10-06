package com.lotfresh.orderservice.domain.orchestrator.step.orderStep;

import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentStep implements WorkflowStep {
    private final PaymentFeignClient feignClient;
    private WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public void process() {
        feignClient.requestPayment();
        changeStatus(WorkflowStepStatus.COMPLETE);
    }

    @Override
    public void revert() {
        feignClient.revertRequestPayment();
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
