package com.lotfresh.orderservice.domain.orchestrator;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
