package com.nion.tasktracker.service;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ITaskService {
    TaskResponse createTask(CreateTaskRequest createTaskRequest);
    TaskResponse updateTask(UpdateTaskRequest updateTaskRequest);
    TaskResponse deleteTask(long userId);
    TaskResponse getTaskById(long userId, long taskId);
    List<TaskResponse> getTaskByUser(long userId);
    List<TaskResponse> getAllTasks();
}
