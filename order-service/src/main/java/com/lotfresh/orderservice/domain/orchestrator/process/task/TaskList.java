package com.lotfresh.orderservice.domain.orchestrator.process.task;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class TaskList {
    List<Task> tasks;

    public void workAll() {
        tasks.forEach(Task::work);
    }
}
