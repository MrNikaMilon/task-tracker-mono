package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.service.ITaskUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskUserService implements ITaskUserService {

    private final ITaskUserRepository taskUserRepository;

    public TaskUserService(ITaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    @Override
    public boolean existsByUserName(String userName) {
        var foundUser = taskUserRepository.findByUsername(userName).orElse(null);
        return foundUser != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        var foundUser = taskUserRepository.findByEmail(email).orElse(null);
        return foundUser != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return taskUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with username: %s not found".formatted(username)));

    }
}
