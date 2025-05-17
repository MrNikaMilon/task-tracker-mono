package com.nion.tasktrackerscheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RequestUserStatistic(@NotBlank String serviceName,
                                   LocalDateTime sendTime) { }