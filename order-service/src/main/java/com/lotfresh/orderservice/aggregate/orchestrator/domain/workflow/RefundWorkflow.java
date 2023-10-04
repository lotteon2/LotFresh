package com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow;


import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;

import java.util.List;

public class RefundWorkflow implements Workflow{
    List<WorkflowStep> workflowSteps;
    @Override
    public List<WorkflowStep> getSteps() {
        return workflowSteps;
    }
}
