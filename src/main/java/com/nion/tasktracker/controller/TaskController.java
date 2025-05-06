package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.service.impl.TaskService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "task controller",
        description = "this is controller for managed task in our api, it support crud operation",
        externalDocs = @ExternalDocumentation(
                description = "link to readme projects",
                url = "https://github.com/MrNikaMilon/task-tracker-app"
        )
)
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "create task endpoint",
            description = "to create task we send info from form and return updated info into front end",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "return info by task",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/tasks")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "data for creating task",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateTaskRequest.class)
                    )
            )
            @RequestBody CreateTaskRequest task
    ) {
        var responseCreatedTask = taskService.createTask(task);
        log.info("task created: {}", responseCreatedTask.id());
        return ResponseEntity.ok(responseCreatedTask);
    }

    @Operation(
            summary = "update task endpoint",
            description = "we may update task info in front end and return new info by task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "return info by task",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    )
            }
    )
    @PutMapping("/tasks/{taskId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "update task data type",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateTaskRequest.class)
                    )
            )
            @RequestBody UpdateTaskRequest task,
            @Parameter(description = "task id for update info", required = true)
            @PathVariable long taskId
    ) {
        var responseUpdatedTask = taskService.updateTask(task, taskId);
        log.info("task updated: {}", responseUpdatedTask);
        return ResponseEntity.ok(responseUpdatedTask);
    }

    @Operation(
            summary = "delete task endpoint",
            description = "we can't delete task from db, and in this endpoint we set a cancel status in task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "return update task info",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/tasks/{taskId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TaskResponse> deleteTask(
            @Parameter(description = "task id for deleted, but in our system deleted equals canceled task", required = true)
            @PathVariable long taskId
    ) {
        var deletedTask = taskService.deleteTask(taskId);
        log.info("task deleted: {}", deletedTask);
        return ResponseEntity.ok(deletedTask);
    }

    @Operation(
            summary = "get task endpoint",
            description = "return task info by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "return info by task",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/tasks/{taskId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public TaskResponse getTaskById(
            @Parameter(description = "for return task we need id task", required = true)
            @PathVariable long taskId
    ) {
        var responseTaskById = taskService.getTaskById(taskId);
        log.info("task by id {}, found: {}", taskId, responseTaskById);
        return responseTaskById;
    }

    @Operation(
            summary = "get task by user",
            description = "return all task by user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "return list task by user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/tasks/users/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<TaskResponse>> getTaskByUser(
            @Parameter(description = "we send user id for return all task by user", required = true)
            @PathVariable long userId
    ) {
        var responseTaskByUser = taskService.getTaskByUser(userId);
        log.info("task by user {}, found: {}", userId, responseTaskByUser);
        return ResponseEntity.ok(responseTaskByUser);
    }

    @Deprecated
    @GetMapping(path = "/tasks/get_all")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var allTasks = taskService.getAllTasks();
        log.info("all tasks found: {}", allTasks);
        return new ResponseEntity<>(allTasks, HttpStatus.TOO_MANY_REQUESTS);
    }

}
