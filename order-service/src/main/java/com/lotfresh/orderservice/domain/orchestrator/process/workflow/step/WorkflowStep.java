package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step;

public interface WorkflowStep {

    boolean isRevertTarget();
    void process();
    void revert();
    void changeStatus(WorkflowStepStatus status);
    WorkflowStepStatus getStatus();


}
