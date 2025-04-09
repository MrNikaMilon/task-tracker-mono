package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.handler.exception.TaskNotFoundException;
import com.nion.tasktracker.mapper.ITaskMapper;
import com.nion.tasktracker.repository.ITaskRepository;
import com.nion.tasktracker.repository.ITaskUserRepository;
import com.nion.tasktracker.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService implements ITaskService {

    private final ITaskUserRepository taskUserRepository;
    private final ITaskRepository taskRepository;
    private final ITaskMapper taskMapper;

    public TaskService(ITaskUserRepository taskUserRepository,
                       ITaskRepository taskRepository,
                       ITaskMapper taskMapper) {
        this.taskUserRepository = taskUserRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        var savedEntity = taskRepository.save(
                taskMapper.toEntity(createTaskRequest, taskUserRepository)
        );
        log.info("created task: {}", savedEntity);
        return taskMapper.toResponse(savedEntity);
    }

    @Transactional
    @Override
    public TaskResponse updateTask(UpdateTaskRequest updateTaskRequest, long taskId) {
        var foundUpdatedTask = taskRepository.findById(taskId).orElseThrow();
        foundUpdatedTask.setTaskName(updateTaskRequest.name());
        foundUpdatedTask.setTaskDescription(updateTaskRequest.description());
        foundUpdatedTask.setStatus(updateTaskRequest.status());
        foundUpdatedTask.setLastEditedAt(LocalDateTime.now());
        var updatedTask = taskRepository.save(foundUpdatedTask);
        return taskMapper.toResponse(updatedTask);
    }

    //by business logic we not real delete task from db, we set a declined status to task
    @Override
    public TaskResponse deleteTask(long userId) {
        var foundTask = taskRepository.findById(userId)
                .orElseThrow(() -> new TaskNotFoundException("not found task with id %d".formatted(userId)));
        foundTask.setStatus(TaskStatus.CANCELED);
        var updatedTask = taskRepository.save(foundTask);
        return taskMapper.toResponse(updatedTask);
    }

    @Override
    public TaskResponse getTaskById(long taskId) {
        var foundTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("not found task with id %d".formatted(taskId)));
        return taskMapper.toResponse(foundTask);
    }

    @Override
    public List<TaskResponse> getTaskByUser(long userId) {
        var foundTasks = taskRepository.getTaskByUser(userId);
        log.info("found tasks by user id: {}, count tasks: {}", userId, foundTasks.size());
        return foundTasks
                .stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        var allTasks = taskRepository.findAll();
        log.info("found all tasks in db: {}", allTasks.size());
        return allTasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
