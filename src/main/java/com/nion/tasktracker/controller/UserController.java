package com.nion.tasktracker.controller;

import com.nion.tasktracker.dto.request.create.CreateUserRequest;
import com.nion.tasktracker.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    //создание юзера
    //получение профиля(юзера)
    //отключение юзера
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest user) throws ServiceNotFoundException {

        throw new ServiceNotFoundException("not implemented yet");
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable long userId) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> disableUser(@PathVariable long userId) throws ServiceNotFoundException {
        throw new ServiceNotFoundException("not implemented yet");
    }
}
