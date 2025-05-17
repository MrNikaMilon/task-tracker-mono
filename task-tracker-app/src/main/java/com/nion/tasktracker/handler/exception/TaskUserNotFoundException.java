package com.nion.tasktracker.handler.exception;

public class TaskUserNotFoundException extends RuntimeException {
    public TaskUserNotFoundException(String message) {
        super(message);
    }
}
