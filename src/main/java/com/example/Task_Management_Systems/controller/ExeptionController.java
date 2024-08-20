package com.example.Task_Management_Systems.controller;

import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExeptionController {
    @ExceptionHandler(TaskMenegmentExeption.class)
    public ResponseEntity<String> handleTaskManagementException(TaskMenegmentExeption ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        if (ex.getMessage().contains("a user with that name already exists")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("the name performer was not found")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("You are not the creator of this task")){
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("You are not the creator or performer of this task")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("not found task")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("not found user")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("You are not performer of this task")) {
            status = HttpStatus.BAD_REQUEST;
<<<<<<< HEAD
        } else if (ex.getMessage().contains("bad token")) {
            status = HttpStatus.FORBIDDEN;
=======
>>>>>>> origin/tz
        }
        return ResponseEntity.status(status).body(message);
    }
}
