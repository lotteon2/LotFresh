package com.lotfresh.orderservice.aggregate.orchestrator.domain;

import com.lotfresh.orderservice.aggregate.orchestrator.domain.step.WorkflowStep;
import com.lotfresh.orderservice.aggregate.orchestrator.domain.workflow.Workflow;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Orchestrator {
    Workflow workflow;

    public void doTransaction() {
        try {
            workflow.getSteps().forEach(WorkflowStep::process);
        } catch (Exception e) {
            revertProcess();
        }
    }
    private void revertProcess() {
        // 에러복구로직이 없음
        // 굳이 동기일 필요가 있는가? -> 카프카?
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }
}
