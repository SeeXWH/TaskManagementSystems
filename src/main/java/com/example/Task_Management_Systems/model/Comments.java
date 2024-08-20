package com.example.Task_Management_Systems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long task;
    private String users;
    private String text;

    public Comments() {
    }

    public Comments(long id, long task, String user, String text) {
        this.id = id;
        this.task = task;
        this.users = user;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTask() {
        return task;
    }

    public void setTask(long task) {
        this.task = task;
    }

    public String getUser() {
        return users;
    }

    public void setUser(String user) {
        this.users = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return id == comments.id && task == comments.task && Objects.equals(users, comments.users) && Objects.equals(text, comments.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, users, text);
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", task=" + task +
                ", user=" + users +
                ", text='" + text + '\'' +
                '}';
    }
}
