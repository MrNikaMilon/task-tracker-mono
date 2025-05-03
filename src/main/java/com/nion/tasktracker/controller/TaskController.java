package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.service.impl.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //создание задачи
    @PostMapping("/tasks")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest task) {
        var responseCreatedTask = taskService.createTask(task);
        log.info("task created: {}", responseCreatedTask.id());
        return ResponseEntity.ok(responseCreatedTask);
    }
    //обновление задачи
    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest task, @PathVariable long taskId) {
        var responseUpdatedTask = taskService.updateTask(task, taskId);
        log.info("task updated: {}", responseUpdatedTask);
        return ResponseEntity.ok(responseUpdatedTask);
    }
    //удаление задачи
    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable long taskId) {
        var deletedTask = taskService.deleteTask(taskId);
        log.info("task deleted: {}", deletedTask);
        return ResponseEntity.ok(deletedTask);
    }
    //получение задачи(по юзеру) + получение задач(по юзеру) + получение всех задач(по всем юзерам)
    @GetMapping("/tasks/{taskId}")
    @PreAuthorize("hasAnyRole('USER_ROLE', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable long taskId) {
        var responseTaskById = taskService.getTaskById(taskId);
        log.info("task by id {}, found: {}", taskId, responseTaskById);
        return ResponseEntity.ok(responseTaskById);
    }

    @GetMapping("/tasks/users/{userId}")
    @PreAuthorize("hasAnyRole('USER_ROLE', 'ROLE_ADMIN')")
    public ResponseEntity<List<TaskResponse>> getTaskByUser(@PathVariable long userId) {
        var responseTaskByUser = taskService.getTaskByUser(userId);
        log.info("task by user {}, found: {}", userId, responseTaskByUser);
        return ResponseEntity.ok(responseTaskByUser);
    }

    @GetMapping(path = "/tasks/get_all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var allTasks = taskService.getAllTasks();
        log.info("all tasks found: {}", allTasks);
        return new ResponseEntity<>(allTasks, HttpStatus.TOO_MANY_REQUESTS);
    }

}
