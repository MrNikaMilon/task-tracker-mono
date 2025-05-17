package com.nion.tasktrackerscheduler.dto.message.to;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RabbitStatisticRequest(LocalDateTime sendingTime,
                                     String type,
                                     String sendingService) { }
