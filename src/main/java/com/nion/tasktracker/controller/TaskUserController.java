package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import com.nion.tasktracker.handler.exception.TaskNotFoundException;
import com.nion.tasktracker.handler.exception.TaskUserNotFoundException;
import com.nion.tasktracker.service.ITaskUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class TaskUserController {

    private final ITaskUserService userService;
    //создание юзера
    //получение профиля(юзера)
    //отключение юзера
    @PostMapping("/users")
    public ResponseEntity<TaskUserResponse> createUser(@RequestBody CreateTaskUserRequest user) {
        var response = userService.createUser(user);
        log.info("created user from controller: {}", response);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<TaskUserResponse> getUserInfo(@PathVariable long userId) {
        var response = userService.getUserById(userId);
        log.info("get user info from controller: {}", response);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<TaskUserResponse> updateUser(@PathVariable long userId, @RequestBody UpdateTaskUserRequest updatedUser) {
        var response = userService.updateUser(
                userId,
                updatedUser
        );
        log.info("updated user from controller: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> disableUser(@PathVariable long userId) {
        var response = userService.disableUser(userId);
        if(!response.active()){
            log.info("disable user from controller: {}", response);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("not disable user from controller because in the date range {}", response.lastActivity());
            return new ResponseEntity<>("user is active by date", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
