package com.nion.tasktrackerpostman.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RabbitRegistrationMessage(
        String email,
        LocalDateTime registrationTime
) { }
