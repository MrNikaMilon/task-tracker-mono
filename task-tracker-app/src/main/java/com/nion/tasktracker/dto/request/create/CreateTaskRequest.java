package com.nion.tasktracker.dto.request.create;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "request for create task by users")
public record CreateTaskRequest(
        @Schema(description = "task names", example = "Write project report")
        String name,
        @Schema(description = "task description with info about tasks too", example = "Complete the final report for the economics project")
        String description,
        @Schema(description = "acceptable types of tasks", allowableValues = {"WORK", "PERSONAL", "URGENT", "ROUTINE", "PROJECT", "REMINDER", "LEISURE", "MISC"})
        TaskType type,
        @Schema(description = "acceptable status of tasks", allowableValues = {"STARTED", "IN_PROGRESS", "CANCELED", "COMPLETED"})
        TaskStatus status,
        @Schema(description = "date created task", example = "2025-01-01T00:00:00")
        LocalDateTime createdAt,
        @Schema(description = "id of the user who created the task and to whom it belongs", example = "2")
        long userId) { }