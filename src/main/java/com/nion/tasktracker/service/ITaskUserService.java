package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import org.springframework.stereotype.Service;


public interface ITaskUserService {
    TaskUserResponse createUser(CreateTaskUserRequest createTaskUserRequest);
    TaskUserResponse getUserById(long userId);
    TaskUserResponse disableUser(long userId);
}
