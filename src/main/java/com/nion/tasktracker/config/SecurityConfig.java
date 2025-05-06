package com.nion.tasktracker.config;


import com.nion.tasktracker.entity.TaskUserEntity;
import com.nion.tasktracker.entity.TokenEntity;
import com.nion.tasktracker.entity.dictionary.TaskUserRole;
import com.nion.tasktracker.handler.TaskAccessDeniedHandler;
import com.nion.tasktracker.handler.TaskLogoutHandler;
import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.security.JwtFilter;
import com.nion.tasktracker.service.ITaskUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    JwtFilter jwtFilter;
    ITaskUserService taskUserService;
    TaskAccessDeniedHandler taskAccessDeniedHandler;
    TaskLogoutHandler taskLogoutHandler;
    ITaskUserRepository taskUserRepository;

    public SecurityConfig(
            JwtFilter jwtFilter,
            ITaskUserService taskUserService,
            TaskAccessDeniedHandler taskAccessDeniedHandler,
            TaskLogoutHandler taskLogoutHandler,
            ITaskUserRepository taskUserRepository) {
        this.jwtFilter = jwtFilter;
        this.taskUserService = taskUserService;
        this.taskAccessDeniedHandler = taskAccessDeniedHandler;
        this.taskLogoutHandler = taskLogoutHandler;
        this.taskUserRepository = taskUserRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var userAdmin = TaskUserEntity.builder()
                .username("admin")
                .email("admin@admin.com")
                .password(passwordEncoder().encode("admin"))
                .role(TaskUserRole.ROLE_ADMIN)
                .build();
        var checkExistsAdmin = taskUserService.existsByUserName(userAdmin.getUsername());
        if(!checkExistsAdmin) {
            taskUserRepository.save(userAdmin);
        }
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers(
                            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                    ).permitAll();
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(taskUserService)
                .exceptionHandling(exceptions -> {
                    exceptions.accessDeniedHandler(taskAccessDeniedHandler);
                    exceptions.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(taskLogoutHandler))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
