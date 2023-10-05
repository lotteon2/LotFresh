package com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
