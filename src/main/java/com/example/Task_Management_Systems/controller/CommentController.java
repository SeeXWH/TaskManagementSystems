package com.example.Task_Management_Systems.controller;

import com.example.Task_Management_Systems.model.Comments;
import com.example.Task_Management_Systems.service.CommentsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/create")
    public Comments createComment(@RequestBody Comments comments, Authentication authentication){
        return commentsService.createComment(comments, authentication);
    }
    @GetMapping("/getComments")
    public List<Comments> getAllComments(@RequestParam("taskId") long taskId){
        return commentsService.getAllComments(taskId);
    }
}
