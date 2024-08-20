package com.example.Task_Management_Systems.repository;

import com.example.Task_Management_Systems.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByTask(long taskId);
}
