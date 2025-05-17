package com.nion.tasktracker.service;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface ITaskUserService extends UserDetailsService{
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
