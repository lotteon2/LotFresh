package com.lotfresh.orderservice.aggregate.orchestrator.domain.step.orderStep;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStepStatus;
import com.lotfresh.orderservice.aggregate.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.aggregate.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.aggregate.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderStep implements WorkflowStep {
    private final OrderService orderService;
    private final OrderCreateRequest orderCreateRequest;
    private OrderCreateResponse orderCreateResponse;
    private WorkflowStepStatus status = WorkflowStepStatus.PENDING;

    @Override
    public boolean isRevertTarget() {
        return this.status == WorkflowStepStatus.COMPLETE;
    }

    @Override
    public void process() {
        orderCreateResponse = orderService.insertOrder(
                orderCreateRequest.getUserId(),orderCreateRequest.getProductRequests());
        changeStatus(WorkflowStepStatus.COMPLETE);
    }

    @Override
    public void revert() {
        orderService.revertInsertOrder(orderCreateResponse);
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
