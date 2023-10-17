package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step;

public interface WorkflowStep {
    abstract public boolean isRevertTarget();
    abstract public Object process();
    abstract public void revert();
    abstract public void changeStatus(WorkflowStepStatus status);
    abstract public WorkflowStepStatus getStatus();
    abstract public String getWorkflowName();

}
