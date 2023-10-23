package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step;

import com.lotfresh.orderservice.exception.SagaException;

public interface WorkflowStep {
    boolean isRevertTarget();
     Object process() throws SagaException;
     void revert();
     void changeStatus(WorkflowStepStatus status);
     WorkflowStepStatus getStatus();
     String getWorkflowName();

}
