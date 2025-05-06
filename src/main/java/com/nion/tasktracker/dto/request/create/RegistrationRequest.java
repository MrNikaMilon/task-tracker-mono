package com.nion.tasktracker.dto.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NonNull;

@Builder
@Schema(description = "request for registration and create user in the system")
public record RegistrationRequest(
        @Schema(description = "user name in system", example = "MrNikaMilon")
        @NonNull @Min(value = 2, message = "Name must be more than 2 characters") @Max(value = 10, message = "Name must be less than 10 characters")
        String username,
        @Schema(description = "user email in system", example = "user.info.goryi@mail.io")
        @NonNull @NotBlank @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Unsupported email format, please check")
        String email,
        @Schema(description = "user passwords in system", example = "45464@t$!")
        @NonNull
        String password) { }
