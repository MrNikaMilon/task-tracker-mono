package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.response.AuthResponse;
import com.nion.tasktracker.dto.request.LoginRequest;
import com.nion.tasktracker.dto.request.create.RegistrationRequest;
import com.nion.tasktracker.dto.response.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    RegistrationResponse registration(RegistrationRequest registrationRequest);
    AuthResponse authentication(LoginRequest request);
    ResponseEntity<AuthResponse> refresh(HttpServletRequest request, HttpServletResponse response);
}
