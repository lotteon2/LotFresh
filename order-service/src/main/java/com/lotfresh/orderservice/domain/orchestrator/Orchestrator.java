package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.workflow.Workflow;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Orchestrator {
    Workflow workflow;

    public void doTransaction() {
        workflow.getSteps().forEach(WorkflowStep::process);
    }
    public void revertProcess() {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }
}
