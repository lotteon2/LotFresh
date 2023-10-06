package com.lotfresh.orderservice.domain.orchestrator.workflow;

import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
