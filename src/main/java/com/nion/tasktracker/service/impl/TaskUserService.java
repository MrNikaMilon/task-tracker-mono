package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import com.nion.tasktracker.entity.dictionary.UserRole;
import com.nion.tasktracker.handler.exception.TaskNotFoundException;
import com.nion.tasktracker.mapper.ITaskUserMapper;
import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.service.ITaskUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskUserService implements ITaskUserService {

    private final ITaskUserRepository taskUserRepository;
    private final ITaskUserMapper taskUserMapper;

    @Override
    public TaskUserResponse createUser(CreateTaskUserRequest createTaskUserRequest) {
        var entityForSave = taskUserMapper.toEntity(createTaskUserRequest);
        entityForSave.setRole(UserRole.USER_ROLE);
        entityForSave.setLastActivity(LocalDateTime.now());
        entityForSave.setRole(UserRole.USER_ROLE);

        entityForSave = taskUserRepository.save(
                entityForSave
        );

        log.info("created user with name: {}", entityForSave.getUserName());
        return taskUserMapper.toResponse(entityForSave);
    }

    @Override
    public TaskUserResponse updateUser(long userId, UpdateTaskUserRequest updateTaskUserRequest) {
        var foundUser = taskUserRepository.findById(userId)
                .orElseThrow(() -> new TaskNotFoundException("not found user with id: %d".formatted(userId)));
        foundUser.setUserName(updateTaskUserRequest.userName());
        foundUser.setEmail(updateTaskUserRequest.email());
        foundUser.setUpdatedAt(LocalDateTime.now());
        foundUser.setLastActivity(LocalDateTime.now());

        log.info("updated user with name: {}", foundUser.getUserName());
        return taskUserMapper.toResponse(taskUserRepository.save(foundUser));
    }

    @Override
    public TaskUserResponse getUserById(long userId) {
        var foundEntity = taskUserRepository.findById(userId)
                .orElseThrow(() -> new TaskNotFoundException("not found user with id: %d".formatted(userId)));
        return taskUserMapper.toResponse(foundEntity);
    }

    @Override
    public TaskUserResponse disableUser(long userId) {
        var foundUser = taskUserRepository.findById(userId)
                .orElseThrow(() -> new TaskNotFoundException("not found user with id: %d".formatted(userId)));
        return taskUserMapper.toResponse(foundUser);
    }
}
