package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Comments;
import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.repository.CommentsRepository;
import com.example.Task_Management_Systems.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceTestUnit {

    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentsService commentsService;

    @Test
    void createComment_shouldCreateComment_whenTaskExists() {
        // given
        Comments comments = new Comments();
        comments.setTask(1L);
        comments.setText("Test comment");

        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(authentication.getName()).thenReturn("testuser");
        when(commentsRepository.save(comments)).thenReturn(comments);

        // when
        Comments createdComment = commentsService.createComment(comments, authentication);

        // then
        assertNotNull(createdComment);
        assertEquals("testuser", createdComment.getUser());
        assertEquals("Test comment", createdComment.getText());
        verify(commentsRepository, times(1)).save(comments);
    }

    @Test
    void createComment_shouldThrowException_whenTaskDoesNotExist() {
        // given
        Comments comments = new Comments();
        comments.setTask(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(TaskMenegmentExeption.class, () -> commentsService.createComment(comments, authentication));
        verify(commentsRepository, never()).save(any());
    }

    @Test
    void getAllComments_shouldReturnComments_whenTaskExists() {
        // given
        long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        Comments comment = new Comments();
        comment.setTask(taskId);
        comment.setText("Test comment");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(commentsRepository.findAllByTask(taskId)).thenReturn(Collections.singletonList(comment));

        // when
        List<Comments> comments = commentsService.getAllComments(taskId);

        // then
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test comment", comments.get(0).getText());
        verify(commentsRepository, times(1)).findAllByTask(taskId);
    }

    @Test
    void getAllComments_shouldThrowException_whenTaskDoesNotExist() {
        // given
        long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(TaskMenegmentExeption.class, () -> commentsService.getAllComments(taskId));
        verify(commentsRepository, never()).findAllByTask(anyLong());
    }
}