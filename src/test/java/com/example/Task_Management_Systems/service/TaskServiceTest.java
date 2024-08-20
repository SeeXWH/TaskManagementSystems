package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.repository.TaskRepository;
import com.example.Task_Management_Systems.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private TaskService taskService;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = new UsernamePasswordAuthenticationToken("testUser", "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void createTask_Success() {
        Task task = new Task();
        task.setUserExecutor("executorUser");

        Users userExecutor = new Users();
        userExecutor.setUserName("executorUser");

        when(usersRepository.findByUsername("executorUser")).thenReturn(userExecutor);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task, authentication);

        assertNotNull(createdTask);
        assertEquals("testUser", createdTask.getUserAuthor());
    }

    @Test
    void createTask_UserExecutorNotFound() {
        Task task = new Task();
        task.setUserExecutor("nonExistingUser");

        when(usersRepository.findByUsername("nonExistingUser")).thenReturn(null);

        assertThrows(TaskMenegmentExeption.class, () -> taskService.createTask(task, authentication));
    }

    @Test
    void editTask_Success() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("testUser");
        existingTask.setUserExecutor("executorUser");

        Task editTask = new Task();
        editTask.setUserExecutor("newExecutorUser");

        Users userExecutor = new Users();
        userExecutor.setUserName("newExecutorUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(usersRepository.findByUsername("newExecutorUser")).thenReturn(userExecutor);
        when(taskRepository.save(any(Task.class))).thenReturn(editTask);

        Task updatedTask = taskService.editTask(editTask, 1L, authentication);

        assertNotNull(updatedTask);
        assertEquals(1L, updatedTask.getId());
        assertEquals("newExecutorUser", updatedTask.getUserExecutor());
    }

    @Test
    void editTask_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskMenegmentExeption.class, () -> taskService.editTask(new Task(), 1L, authentication));
    }

    @Test
    void editTask_NotAuthor() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("anotherUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        assertThrows(TaskMenegmentExeption.class, () -> taskService.editTask(new Task(), 1L, authentication));
    }

    @Test
    void editTask_UserExecutorNotFound() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("testUser");

        Task editTask = new Task();
        editTask.setUserExecutor("nonExistingUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(usersRepository.findByUsername("nonExistingUser")).thenReturn(null);

        assertThrows(TaskMenegmentExeption.class, () -> taskService.editTask(editTask, 1L, authentication));
    }

    @Test
    void deleteTask_Success() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("testUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        String result = taskService.deleteTask(1L, authentication);

        assertEquals("successfully", result);
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void deleteTask_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskMenegmentExeption.class, () -> taskService.deleteTask(1L, authentication));
    }

    @Test
    void deleteTask_NotAuthor() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("anotherUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        assertThrows(TaskMenegmentExeption.class, () -> taskService.deleteTask(1L, authentication));
    }

    @Test
    void getTaskById_Success_Author() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("testUser");
        existingTask.setUserExecutor("someExecutor");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        Task retrievedTask = taskService.getTaskById(1L, authentication);

        assertNotNull(retrievedTask);
        assertEquals(1L, retrievedTask.getId());
    }

    @Test
    void getTaskById_Success_Executor() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserExecutor("testUser");
        existingTask.setUserAuthor("someAuthor");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        Task retrievedTask = taskService.getTaskById(1L, authentication);

        assertNotNull(retrievedTask);
        assertEquals(1L, retrievedTask.getId());
    }

    @Test
    void getTaskById_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskMenegmentExeption.class, () -> taskService.getTaskById(1L, authentication));
    }

    @Test
    void getTaskById_NotAuthorOrExecutor() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserAuthor("anotherUser");
        existingTask.setUserExecutor("anotherExecutor");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        assertThrows(TaskMenegmentExeption.class, () -> taskService.getTaskById(1L, authentication));
    }

    @Test
    void getAllTask_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());

        when(taskRepository.findTasksByUser("testUser")).thenReturn(tasks);

        List<Task> retrievedTasks = taskService.getAllTask(authentication);

        assertNotNull(retrievedTasks);
        assertEquals(2, retrievedTasks.size());
    }

    @Test
    void getAllTaskUser_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());

        Users user = new Users();
        user.setUserName("testUser");

        when(usersRepository.findByUsername("testUser")).thenReturn(user);
        when(taskRepository.findTasksByUser("testUser")).thenReturn(tasks);

        List<Task> retrievedTasks = taskService.getAllTaskUser("testUser");

        assertNotNull(retrievedTasks);
        assertEquals(2, retrievedTasks.size());
    }

    @Test
    void getAllTaskUser_UserNotFound() {
        when(usersRepository.findByUsername("nonExistingUser")).thenReturn(null);

        assertThrows(TaskMenegmentExeption.class, () -> taskService.getAllTaskUser("nonExistingUser"));
    }

    @Test
    void setStatus_Success() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserExecutor("testUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task updatedTask = taskService.setStatus(1L, "In Progress", authentication);

        assertNotNull(updatedTask);
        assertEquals("In Progress", updatedTask.getStatus());
    }

    @Test
    void setStatus_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskMenegmentExeption.class, () -> taskService.setStatus(1L, "In Progress", authentication));
    }

    @Test
    void setStatus_NotExecutor() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUserExecutor("anotherUser");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        assertThrows(TaskMenegmentExeption.class, () -> taskService.setStatus(1L, "In Progress", authentication));
    }
}