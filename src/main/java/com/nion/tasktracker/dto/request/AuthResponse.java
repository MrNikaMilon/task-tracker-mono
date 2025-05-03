package com.nion.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record AuthResponse(
        String email,
        String authToken,
        String refreshToken) { }
