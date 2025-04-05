package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import com.nion.tasktracker.service.ITaskUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final ITaskUserService userService;
    //создание юзера
    //получение профиля(юзера)
    //отключение юзера
    @PostMapping("/users")
    public ResponseEntity<TaskUserResponse> createUser(@RequestBody CreateTaskUserRequest user) throws ServiceNotFoundException {
        var response = userService.createUser(user);
        log.info("created user from controller: {}", response);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<TaskUserResponse> getUserProfile(@PathVariable long userId) throws ServiceNotFoundException {
        var response = userService.getUserById(userId);
        log.info("get user profile from controller: {}", response);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> disableUser(@PathVariable long userId) throws ServiceNotFoundException {
        userService.disableUser(userId);
        log.info("disable user from controller: {}", userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
