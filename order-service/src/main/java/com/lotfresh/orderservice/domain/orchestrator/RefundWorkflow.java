package com.lotfresh.orderservice.domain.orchestrator;


import java.util.List;

public class RefundWorkflow implements Workflow{
    List<WorkflowStep> workflowSteps;
    @Override
    public List<WorkflowStep> getSteps() {
        return workflowSteps;
    }
}
