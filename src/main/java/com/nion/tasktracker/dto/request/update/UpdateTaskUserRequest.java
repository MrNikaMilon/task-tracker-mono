package com.nion.tasktracker.dto.request.update;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record UpdateTaskUserRequest(@NonNull @Min(2) @Max(10)
                                String userName,
                                    @NonNull @NotBlank @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
                                String email) { }
