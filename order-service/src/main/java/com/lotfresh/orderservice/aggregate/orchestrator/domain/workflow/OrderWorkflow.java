package com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;
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
