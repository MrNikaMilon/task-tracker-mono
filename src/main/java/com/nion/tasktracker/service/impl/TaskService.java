package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.service.ITaskService;

import java.util.Collection;
import java.util.List;

public class TaskService implements ITaskService {
    @Override
    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        return null;
    }

    @Override
    public TaskResponse updateTask(UpdateTaskRequest updateTaskRequest) {
        return null;
    }

    @Override
    public TaskResponse deleteTask(long userId) {
        return null;
    }

    @Override
    public TaskResponse getTaskById(long userId, long taskId) {
        return null;
    }

    @Override
    public List<TaskResponse> getTaskByUser(long userId) {
        return List.of();
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return List.of();
    }
}
