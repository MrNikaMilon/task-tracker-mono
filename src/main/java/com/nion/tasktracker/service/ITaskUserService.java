package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.response.TaskUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface ITaskUserService extends UserDetailsService{
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
