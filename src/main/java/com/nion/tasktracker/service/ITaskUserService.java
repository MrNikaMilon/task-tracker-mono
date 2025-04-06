package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;


public interface ITaskUserService {
    TaskUserResponse createUser(CreateTaskUserRequest createTaskUserRequest);
    TaskUserResponse updateUser(long userId, UpdateTaskUserRequest updateTaskUserRequest);
    TaskUserResponse getUserById(long userId);
    TaskUserResponse disableUser(long userId);
}
