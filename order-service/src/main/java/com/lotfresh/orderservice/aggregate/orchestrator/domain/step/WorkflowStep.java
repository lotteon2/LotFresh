package com.lotfresh.orderservice.aggregate.orchestrator.domain.step;

public interface WorkflowStep {

    boolean isRevertTarget();
    void process();
    void revert();
    void changeStatus(WorkflowStepStatus status);
    WorkflowStepStatus getStatus();


}
