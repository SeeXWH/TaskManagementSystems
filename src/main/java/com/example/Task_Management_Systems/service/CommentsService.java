package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Comments;
import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.repository.CommentsRepository;
import com.example.Task_Management_Systems.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final TaskRepository taskRepository;

    public CommentsService(CommentsRepository commentsRepository, TaskRepository taskRepository) {
        this.commentsRepository = commentsRepository;
        this.taskRepository = taskRepository;
    }

    public Comments createComment(Comments comments, Authentication authentication){
        if (taskRepository.findById(comments.getTask()).isEmpty()){
            throw new TaskMenegmentExeption("not found task");
        }
        comments.setUser(authentication.getName());
        return commentsRepository.save(comments);
    }
    public List<Comments> getAllComments(long taskId){
        if (taskRepository.findById(taskId).isEmpty()){
            throw new TaskMenegmentExeption("not found task");
        }
        return commentsRepository.findAllByTask(taskId);
    }
}
