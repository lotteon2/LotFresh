package com.lotfresh.orderservice.domain.orchestrator;

public interface WorkflowStep {

    WorkflowStepStatus getStatus();
    void process();
    void revert();
    void changeStatus(WorkflowStepStatus status);


}
