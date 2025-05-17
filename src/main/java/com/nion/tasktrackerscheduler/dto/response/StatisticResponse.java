package com.nion.tasktrackerscheduler.dto.response;

import lombok.Builder;

@Builder
public record StatisticResponse(String email,
                                long uncompletedTask,
                                long completedTask) { }
