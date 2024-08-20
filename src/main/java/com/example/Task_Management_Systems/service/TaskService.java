package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.repository.TaskRepository;
import com.example.Task_Management_Systems.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public final UsersRepository usersRepository;

    public TaskService(TaskRepository taskRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
    }

    public Task createTask(Task task, Authentication authentication) {
        task.setUserAuthor(authentication.getName());
        Users userExecutor = usersRepository.findByUsername(task.getUserExecutor());
        if (userExecutor == null) {
            throw new TaskMenegmentExeption("the name performer was not found");
        }
        return taskRepository.save(task);
    }

    public Task editTask(Task editTask, long id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskMenegmentExeption("not found task"));
        if (!task.getUserAuthor().equals(authentication.getName())){
            throw new TaskMenegmentExeption("You are not the creator of this task");
        }
        Users userExecutor = usersRepository.findByUsername(editTask.getUserExecutor());
        if (userExecutor == null) {
            throw new TaskMenegmentExeption("the name performer was not found");
        }
        editTask.setId(id);
        return taskRepository.save(task);
    }


    public String deleteTask(long id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskMenegmentExeption("not found task"));
        if (!task.getUserAuthor().equals(authentication.getName())) {
            throw new TaskMenegmentExeption("You are not the creator of this task");
        }
        taskRepository.delete(task);
        return "successfully";
    }

    public Task getTaskById(long id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskMenegmentExeption("not found task"));
        if (!task.getUserAuthor().equals(authentication.getName()) || !task.getUserExecutor().equals(authentication.getName())) {
            throw new TaskMenegmentExeption("You are not the creator or performer of this task");
        } else {
            return task;
        }

    }

    public List<Task> getAllTask(Authentication authentication) {
        return taskRepository.findTasksByUser(authentication.getName());
    }

    public List<Task> getAllTaskUser(String user) {
        if (usersRepository.findByUsername(user) == null) {
            throw new TaskMenegmentExeption("not found user");
        }
        return taskRepository.findTasksByUser(user);
    }

    public Task setStatus(long id, String status, Authentication authentication){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskMenegmentExeption("not found task"));
        if (!task.getUserExecutor().equals(authentication.getName())){
            throw new TaskMenegmentExeption("You are not performer of this task");
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }

}
