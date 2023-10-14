package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step;

public abstract class WorkflowStep {
    public WorkflowStepStatus status = WorkflowStepStatus.PENDING;
    abstract public boolean isRevertTarget();
    abstract public void process();
    abstract public void revert();
    abstract public void changeStatus(WorkflowStepStatus status);
    abstract public WorkflowStepStatus getStatus();


}
