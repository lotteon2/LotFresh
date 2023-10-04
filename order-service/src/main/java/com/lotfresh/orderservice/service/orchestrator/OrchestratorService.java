package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowGenerator;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final WorkflowGenerator workflowGenerator;

    public Orchestrator orderTransaction(OrderCreateRequest orderCreateRequest) {
        Workflow orderWorkflow = workflowGenerator.generateOrderWorkflow(orderCreateRequest);
        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .build();
        orderOrchestrator.doTransaction();
        return orderOrchestrator;
    }

}
