package com.nion.tasktracker.exception;

public class TaskUserNotFoundException extends RuntimeException {
    public TaskUserNotFoundException(String message) {
        super(message);
    }
}
