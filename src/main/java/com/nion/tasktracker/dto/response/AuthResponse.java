package com.nion.tasktracker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "returned info by email and token by users")
public record AuthResponse(
        @Schema(description = "this is user email", example = "chelovek.s.goryi@mail.io")
        String email,
        @Schema(description = "standard auth token for user", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzQ2MjcxMjM1LCJleHAiOjE3NDYzMDcyMzV9.YtCm4L8ZxmSBmpsISSnxgKFC9sQoOKcOiZMivHlCVwDEvt-w_wZyLC4h9PTK0Mnt")
        String authToken,
        @Schema(description = "standard refresh token for user", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzQ2MjcxMjM1LCJleHAiOjE3NDYzMDcyMzV9.YtCm4L8ZxmSBmpsISSnxgKFC9sQoOKcOiZMivHlCVwDEvt-w_wZyLC4h9PTK0Mnt")
        String refreshToken) { }
