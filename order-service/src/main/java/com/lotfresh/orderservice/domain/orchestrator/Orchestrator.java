package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.process.workflow.Workflow;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;


@Builder
@Getter
public class Orchestrator {
    Workflow workflow;
    Map<String,Object> workflowResults;
    public void doTransaction() {
        workflowResults = workflow.getSteps().stream()
                .collect(Collectors.toMap(
                        WorkflowStep::getWorkflowName,
                        WorkflowStep::process
                ));
    }
    public void revertProcess() {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }
}
