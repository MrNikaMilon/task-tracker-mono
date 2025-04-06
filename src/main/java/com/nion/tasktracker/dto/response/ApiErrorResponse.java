package com.nion.tasktracker.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ApiErrorResponse(
        int status,
        String message,
        List<FieldError> errors
) {
    @Builder
    public record FieldError(
            String field,
            String message
    ) { }
}
