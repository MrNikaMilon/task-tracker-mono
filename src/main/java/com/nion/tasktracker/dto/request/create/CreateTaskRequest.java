package com.nion.tasktracker.dto.request.create;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import lombok.Builder;

@Builder
public record CreateTaskRequest(String name,
                                String description,
                                TaskType type,
                                TaskStatus status,
                                long userId) { }