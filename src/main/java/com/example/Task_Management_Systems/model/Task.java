package com.example.Task_Management_Systems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String head;
    private String description;
    private String status;
    private String priority;
    private String userAuthor;
    private String userExecutor;

    public Task() {
    }

    public Task(long id, String head, String description, String status, String priority, String userAuthor, String userExecutor) {
        this.id = id;
        this.head = head;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.userAuthor = userAuthor;
        this.userExecutor = userExecutor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(String userAuthor) {
        this.userAuthor = userAuthor;
    }

    public String getUserExecutor() {
        return userExecutor;
    }

    public void setUserExecutor(String userExecutor) {
        this.userExecutor = userExecutor;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(userAuthor, task.userAuthor) && Objects.equals(userExecutor, task.userExecutor) && Objects.equals(head, task.head) && Objects.equals(description, task.description) && Objects.equals(status, task.status) && Objects.equals(priority, task.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, head, description, status, priority, userAuthor, userExecutor);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", head='" + head + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", userAuthor=" + userAuthor +
                ", userExecutor=" + userExecutor +
                '}';
    }
}
