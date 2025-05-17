package com.nion.tasktrackerpostman.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RabbitStatisticMessage(String email,
                                     long uncompletedTask,
                                     long completedTask,
                                     long allTask) { }
