package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.response.AuthResponse;
import com.nion.tasktracker.dto.request.LoginRequest;
import com.nion.tasktracker.dto.request.create.RegistrationRequest;
import com.nion.tasktracker.dto.response.RegistrationResponse;
import com.nion.tasktracker.entity.TaskUserEntity;
import com.nion.tasktracker.entity.TokenEntity;
import com.nion.tasktracker.entity.dictionary.TaskUserRole;
import com.nion.tasktracker.mapper.ITaskUserMapper;
import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.repository.ITokenRepository;
import com.nion.tasktracker.security.JwtService;
import com.nion.tasktracker.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthService implements IAuthService {

    JwtService jwtService;
    ITokenRepository tokenRepository;
    ITaskUserRepository taskUserRepository;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    ITaskUserMapper taskUserMapper;

    public AuthService(JwtService jwtService,
                       ITokenRepository tokenRepository,
                       ITaskUserRepository taskUserRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.taskUserRepository = taskUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public RegistrationResponse registration(RegistrationRequest registrationRequest){
        var registrationUser = TaskUserEntity.builder()
                .username(registrationRequest.username())
                .email(registrationRequest.email())
                .build();
        registrationUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
        registrationUser.setRole(TaskUserRole.ROLE_USER);
        registrationUser.setLastActivity(LocalDateTime.now());

        var savedUser = taskUserRepository.save(registrationUser);

        return RegistrationResponse.builder()
                .username(savedUser.getUsername())
                .tokens(tokenRepository
                        .findAllTokensByUser(savedUser.getUserId())
                        .stream()
                        .map(TokenEntity::getAccessToken)
                        .toArray(String[]::new))
                .build();

    }

    public AuthResponse authentication(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = taskUserRepository.findByUsername(request.username())
                .orElseThrow();

        var authToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllToken(user);
        saveUserTokens(user, authToken, refreshToken);

        return AuthResponse.builder()
                .email(user.getUsername())
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request, HttpServletResponse response) {
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var token = authorizationHeader.substring(7);

        var userId = jwtService.extractUserId(token);

        var user = taskUserRepository.findByUserId(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("user by id: %s not found".formatted(userId)));

        var check = jwtService.isValidRefresh(token);

        if(check){
            log.info("start service to send a new refresh token");
            var newAccessToken = jwtService.generateAccessToken(user);
            var newRefreshToken = jwtService.generateRefreshToken(user);

            revokeAllToken(user);
            saveUserTokens(user, newAccessToken, newRefreshToken);

            return new ResponseEntity<>(AuthResponse.builder()
                    .email(user.getEmail())
                    .authToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build(),
                    HttpStatus.OK);
        }
        log.info("error authorize for refresh token: {}", token);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /** private method for save all token by user*/
    private void saveUserTokens(TaskUserEntity user, String accessToken, String refreshToken) {
        var token = TokenEntity.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isExpired(false)
                .isRevoked(false)
                .taskUserEntity(user)
                .build();
        tokenRepository.save(token);
    }

    /** private method for revoke all token by user*/
    private void revokeAllToken(TaskUserEntity user) {
        var allTokens = tokenRepository.findAllTokensByUser(user.getUserId());

        if(!allTokens.isEmpty()) {
            allTokens.forEach(token -> token.setRevoked(true));
        }

        tokenRepository.saveAll(allTokens);
    }


}
