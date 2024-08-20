package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.dto.AuthRequest;
import com.example.Task_Management_Systems.dto.AuthResponse;
import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.repository.UsersRepository;
import com.example.Task_Management_Systems.security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Service
public class UsersService  {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    public UsersService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addUser(Users users){
        if (usersRepository.findByUsername(users.getUserName()) != null) {
            String errorMessage = "a user with that name already exists";
            throw new TaskMenegmentExeption(errorMessage);
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }



}
