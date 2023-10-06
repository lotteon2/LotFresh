package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.workflow.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.workflow.WorkflowGenerator;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
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
