package com.lotfresh.orderservice.service.orchestrator.steps;

import com.lotfresh.orderservice.domain.orchestrator.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStepStatus;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.service.order.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderStep implements WorkflowStep {
    private final OrderService orderService;
    private final OrderCreateRequest orderCreateRequest;
    private WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public WorkflowStepStatus getStatus() {
        return status;
    }

    @Override
    public void process() {
        orderService.insertOrder(orderCreateRequest);
        changeStatus(WorkflowStepStatus.COMPLETE);
    }

    @Override
    public void revert() {
        orderService.revertInsertOrder(orderCreateRequest);
        changeStatus(WorkflowStepStatus.FAILED);
    }

    @Override
    public void changeStatus(WorkflowStepStatus status) {
        this.status = status;
    }
}
