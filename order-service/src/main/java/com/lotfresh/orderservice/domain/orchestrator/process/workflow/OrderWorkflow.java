package com.lotfresh.orderservice.domain.orchestrator.process.workflow;

import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
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
