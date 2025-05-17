package com.nion.tasktrackerscheduler.dto.message.to;

import lombok.Builder;

@Builder
public record RabbitStatisticNotifyMessage(String email,
                                           long uncompletedTask,
                                           long completedTask) { }
