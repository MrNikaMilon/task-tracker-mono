package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateUserRequest;
import com.nion.tasktracker.dto.response.UserResponse;
import com.nion.tasktracker.service.IUserService;

public class UserService implements IUserService {
    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public UserResponse getUserProfile(long userId) {
        return null;
    }

    @Override
    public UserResponse disableUser(long userId) {
        return null;
    }
}
