package com.nion.tasktracker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "response after registration user in system")
public record RegistrationResponse(
        @Schema(description = "this is user name", example = "MrNikaMilon")
        String username,
        @Schema(description = "standard token for auth users in system", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzQ2MjcxMjM1LCJleHAiOjE3NDYzMDcyMzV9.YtCm4L8ZxmSBmpsISSnxgKFC9sQoOKcOiZMivHlCVwDEvt-w_wZyLC4h9PTK0Mnt")
        String[] tokens) { }
