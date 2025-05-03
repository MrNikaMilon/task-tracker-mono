package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.AuthResponse;
import com.nion.tasktracker.dto.request.LoginRequest;
import com.nion.tasktracker.dto.request.create.RegistrationRequest;
import com.nion.tasktracker.service.impl.AuthService;
import com.nion.tasktracker.service.impl.TaskUserService;
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
public class AuthController {

    private final AuthService authService;
    private final TaskUserService taskUserService;

    public AuthController(AuthService authService, TaskUserService taskUserService) {
        this.authService = authService;
        this.taskUserService = taskUserService;
    }

    @PostMapping(path = "/registration")
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

    @PostMapping(path = "/login")
    public ResponseEntity<?> authentication(@RequestBody LoginRequest loginRequest) {
        var request = authService.authentication(loginRequest);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refresh(request, response);
    }

}
