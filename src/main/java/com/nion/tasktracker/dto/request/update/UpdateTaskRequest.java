package com.nion.tasktracker.dto.request.update;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UpdateTaskRequest(String name,
                                String description,
                                TaskType type,
                                TaskStatus status,
                                LocalDateTime createdAt,
                                long userId) { }

