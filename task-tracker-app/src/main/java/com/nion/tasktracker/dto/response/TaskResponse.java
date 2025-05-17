package com.nion.tasktracker.dto.response;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(long id,
                           String name,
                           String description,
                           TaskType type,
                           TaskStatus status,
                           LocalDateTime createdAt,
                           String userName,
                           String userEmail) {
}