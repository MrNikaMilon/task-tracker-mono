package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.response.AuthResponse;
import com.nion.tasktracker.dto.request.LoginRequest;
import com.nion.tasktracker.dto.request.create.RegistrationRequest;
import com.nion.tasktracker.service.impl.AuthService;
import com.nion.tasktracker.service.impl.TaskUserService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name = "auth controller",
        description = "this is controller for authorization, login and refreshed available token users",
        externalDocs = @ExternalDocumentation(
                description = "link to readme projects",
                url = "https://github.com/MrNikaMilon/task-tracker-app"
        )
)
public class AuthController {

    private final AuthService authService;
    private final TaskUserService taskUserService;

    public AuthController(AuthService authService, TaskUserService taskUserService) {
        this.authService = authService;
        this.taskUserService = taskUserService;
    }

    @Operation(
            summary = "create and registration users in api",
            description = "registration user and create in the system new entity"
    )
    @PostMapping(path = "/registration")
    @ApiResponse(responseCode = "400", description = "we may return 400 error when name or user name already exists in our db")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        if(taskUserService.existsByUserName(registrationRequest.username())) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        if(taskUserService.existsByEmail(registrationRequest.email())) {
            return ResponseEntity.badRequest().body("email already exists");
        }
        authService.registration(registrationRequest);
        return ResponseEntity.ok("successful registration");
    }

    @Operation(
            summary = "login users in api",
            description = "method return user email and refresh and auth token for another system operations"
    )
    @PostMapping(path = "/login")
    public ResponseEntity<?> authentication(@RequestBody LoginRequest loginRequest) {
        var request = authService.authentication(loginRequest);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(
            summary = "refresh users token in api",
            description = "we put an a refresh token and return new refresh token for users"
    )
    @PostMapping("/refresh_token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refresh(request, response);
    }

}
