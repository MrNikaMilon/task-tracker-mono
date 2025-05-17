package com.nion.tasktrackerscheduler.dto.message.from;

import lombok.*;

@Builder
public record RabbitStatisticMessage(String email,
                                     long uncompletedTask,
                                     long completedTask,
                                     long allTask) { }