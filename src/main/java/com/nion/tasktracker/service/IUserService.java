package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.request.create.CreateUserRequest;
import com.nion.tasktracker.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse getUserProfile(long userId);
    UserResponse disableUser(long userId);
}
