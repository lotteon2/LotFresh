package com.lotfresh.orderservice.domain.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class OrderWorkflow implements Workflow{
    List<WorkflowStep> workflowSteps;
    @Override
    public List<WorkflowStep> getSteps() {
        return workflowSteps;
    }
}
