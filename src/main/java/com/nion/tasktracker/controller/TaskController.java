package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class TaskController {

    //создание задачи
    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest task) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }
    //обновление задачи
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest task, @PathVariable long taskId) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }
    //удаление задачи
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable long taskId) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }
    //получение задачи(по юзеру) + получение задач(по юзеру) + получение всех задач(по всем юзерам)
    @GetMapping("/tasks/user/{userId}/id/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable long userId, @PathVariable long taskId) throws ServiceNotFoundException {

        throw new ServiceNotFoundException("not implemented yet");
    }

    @GetMapping("tasks/{userId}")
    public ResponseEntity<List<TaskResponse>> getTaskByUser(@PathVariable long userId) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable long id) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }

}
