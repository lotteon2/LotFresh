package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.process.task.TaskList;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.Workflow;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Builder
@Getter
public class Orchestrator {
    Workflow workflow;
    TaskList afterSuccessTasks;
    boolean isSuccessed;
    Map<String,Object> workflowResults;
    public void doTransaction() {
        workflowResults = workflow.getSteps().stream()
                .collect(Collectors.toMap(
                        WorkflowStep::getWorkflowName,
                        WorkflowStep::process
                ));
        isSuccessed = true;
    }
    public void revertProcess() {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }
    public void doAfterSuccess() {
        afterSuccessTasks.workAll();
    }
}
