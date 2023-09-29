package com.lotfresh.orderservice.domain.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Orchestrator {
    private final List<WorkflowStep> steps;
}
