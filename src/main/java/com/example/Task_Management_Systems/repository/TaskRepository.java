package com.example.Task_Management_Systems.repository;

import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserAuthor(String userAuthor);
    @Query("SELECT t FROM Task t WHERE t.userAuthor = :user OR t.userExecutor = :user")
    List<Task> findTasksByUser(@Param("user") String user);
}
