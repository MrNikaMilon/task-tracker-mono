package com.nion.tasktracker.service.impl;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.repository.ITaskRepository;
import com.nion.tasktracker.service.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

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
