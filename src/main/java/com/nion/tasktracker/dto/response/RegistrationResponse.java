package com.nion.tasktracker.dto.response;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        String username,
        String[] tokens) { }
