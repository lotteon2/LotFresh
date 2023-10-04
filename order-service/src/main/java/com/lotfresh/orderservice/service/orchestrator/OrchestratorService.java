package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowGenerator;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final WorkflowGenerator workflowGenerator;

    public void orderTransaction(OrderCreateRequest orderCreateRequest) {
        // 리턴값을 주는게 어떤지?
        Workflow orderWorkflow = workflowGenerator.generateOrderWorkflow(orderCreateRequest);
        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .build();
        orderOrchestrator.doTransaction();
    }

    public void refundTransaction(OrderRefundRequest orderRefundRequest) {
        Workflow refundWorkflow = workflowGenerator.generateRefundWorkflow(orderRefundRequest);
        Orchestrator refundOrchestrator = Orchestrator.builder()
                .workflow(refundWorkflow)
                .build();
        refundOrchestrator.doTransaction();
    }


}
