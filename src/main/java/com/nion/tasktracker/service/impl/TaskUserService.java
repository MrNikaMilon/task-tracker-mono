package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.service.ITaskUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskUserService implements ITaskUserService {

    private final ITaskUserRepository taskUserRepository;

    public TaskUserService(ITaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    @Override
    public TaskUserResponse createUser(CreateTaskUserRequest createTaskUserRequest) {
        return null;
    }

    @Override
    public TaskUserResponse getUserById(long userId) {
        taskUserRepository.findById(userId);
        return null;
    }

    @Override
    public TaskUserResponse disableUser(long userId) {
        return null;
    }
}
