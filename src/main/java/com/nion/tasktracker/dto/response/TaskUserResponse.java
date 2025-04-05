package com.nion.tasktracker.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskUserResponse(long id,
                               String name,
                               String email,
                               String role,
                               boolean active,
                               LocalDateTime lastActivity) { }

