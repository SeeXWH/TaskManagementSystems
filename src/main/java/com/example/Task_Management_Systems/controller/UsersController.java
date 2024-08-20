package com.example.Task_Management_Systems.controller;

import com.example.Task_Management_Systems.dto.AuthRequest;
import com.example.Task_Management_Systems.dto.AuthResponse;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.security.JwtTokenProvider;
import com.example.Task_Management_Systems.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/user")
public class UsersController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    public UsersController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public Users addUser(@RequestBody Users users) {
        return usersService.addUser(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        return usersService.login(authRequest, request);
    }
}
