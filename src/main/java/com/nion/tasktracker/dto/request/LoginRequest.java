package com.nion.tasktracker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "dto for login requests user")
public record LoginRequest(
        @Schema(description = "username users in system", example = "MrNikaMilon")
        String username,
        @Schema(description = "password users in system", example = "47699wq@!S")
        String password) { }
