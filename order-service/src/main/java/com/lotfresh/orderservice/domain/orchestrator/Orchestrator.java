package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.process.afterSuccess.TaskList;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.Workflow;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Orchestrator {
    Workflow workflow;
    TaskList taskList;
    boolean isSuccessed;
    public void doTransaction() {
        workflow.getSteps().forEach(WorkflowStep::process);
        isSuccessed = true;
    }
    public void revertProcess() {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }
    public void afterSuccess() {
        taskList.workAll();
    }
}
