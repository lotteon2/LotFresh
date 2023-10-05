package com.lotfresh.orderservice.aggregate.orchestrator.service;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.Orchestrator;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow.Workflow;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow.WorkflowGenerator;
import com.lotfresh.orderservice.aggregate.orchestrator.controller.request.OrderCreateRequest;
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
