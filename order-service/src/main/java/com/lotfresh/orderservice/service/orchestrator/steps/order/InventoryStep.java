package com.lotfresh.orderservice.service.orchestrator.steps.order;

import com.lotfresh.orderservice.domain.orchestrator.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStepStatus;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.service.orchestrator.feigns.InventoryFeignClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InventoryStep implements WorkflowStep {
    private final InventoryFeignClient feignClient;
    private final List<ProductRequest> productRequests;
    private WorkflowStepStatus status = WorkflowStepStatus.PENDING;


    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public void process() {
        feignClient.deductQuantity(productRequests);
        changeStatus(WorkflowStepStatus.COMPLETE);
    }

    @Override
    public void revert() {
        feignClient.revertDeductQuantity(productRequests);
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
