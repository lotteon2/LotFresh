package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.*;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final WorkflowGenerator workflowGenerator;

    public void orderTransaction(OrderCreateRequest orderCreateRequest) {
        OrderWorkflow orderWorkflow = workflowGenerator.generateOrderWorkflow(orderCreateRequest);
        doTransaction(orderWorkflow);
    }

    public void refundTransaction(OrderRefundRequest orderRefundRequest) {
        RefundWorkflow refundWorkflow = workflowGenerator.generateRefundWorkflow(orderRefundRequest);
        doTransaction(refundWorkflow);
    }

    public void doTransaction(Workflow workflow) {
        try {
            workflow.getSteps().stream()
                    .forEach(WorkflowStep::process);
        } catch (Exception e) {
            revertProcess(workflow);
        }
    }

    private void revertProcess(Workflow workflow) {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }

}
