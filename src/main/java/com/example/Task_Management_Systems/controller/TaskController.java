package com.example.Task_Management_Systems.controller;

import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/create")
    public Task createTask(@RequestBody Task task, Authentication authentication){
        return taskService.createTask(task, authentication);
    }

    @PutMapping("/edit")
    public Task editTask(@RequestBody Task task, @RequestParam("id") long id, Authentication authentication){
        return taskService.editTask(task, id, authentication);
    }
    @DeleteMapping("/delete")
    public String deleteTask(@RequestParam("id") long id, Authentication authentication){
        return taskService.deleteTask(id, authentication);
    }

    @GetMapping("/getTask")
    public Task getTaskById(@RequestParam("id") long id, Authentication authentication){
        return taskService.getTaskById(id, authentication);
    }

    @GetMapping("/myTask")
    public List<Task> getMyTasks(Authentication authentication) {
        return taskService.getAllTask(authentication);
    }
    @GetMapping("/userTask")
    public List<Task> getAllTaskUser(String user){
        return taskService.getAllTaskUser(user);
    }

    @PutMapping("/setStatus")
    public Task setStatus(@RequestParam("id") long id, @RequestParam("status") String status, Authentication authentication){
        return  taskService.setStatus(id, status, authentication);
    }
}
