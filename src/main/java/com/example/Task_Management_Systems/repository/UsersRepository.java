package com.example.Task_Management_Systems.repository;

import com.example.Task_Management_Systems.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String userName);
    @Query("SELECT u.id FROM Users u WHERE u.username = :name")
    Long findIdByUsername(@Param("name") String name);
}
