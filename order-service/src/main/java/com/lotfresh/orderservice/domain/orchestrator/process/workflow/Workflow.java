package com.lotfresh.orderservice.domain.orchestrator.process.workflow;

import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
