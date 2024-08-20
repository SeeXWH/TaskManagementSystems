package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.dto.AuthRequest;
import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.repository.UsersRepository;
import com.example.Task_Management_Systems.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTestUnit {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @Test
    void addUser_ShouldSaveUser_WhenUserDoesNotExist() {
        Users user = new Users();
        user.setUserName("testuser");
        user.setPassword("password");

        when(usersRepository.findByUsername(user.getUserName())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(usersRepository.save(user)).thenReturn(user);

        Users savedUser = usersService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUserName());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void addUser_ShouldThrowException_WhenUserAlreadyExists() {
        Users user = new Users();
        user.setUserName("testuser");
        user.setPassword("password");

        when(usersRepository.findByUsername(user.getUserName())).thenReturn(user);

        assertThrows(TaskMenegmentExeption.class, () -> usersService.addUser(user));
    }

    @Test
    void login_ShouldReturnToken_WhenAuthenticationSuccessful() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        HttpServletRequest request = new MockHttpServletRequest();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("testToken");

        String token = usersService.login(authRequest, request);

        assertEquals("token: testToken", token);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");

        HttpServletRequest request = new MockHttpServletRequest();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new TaskMenegmentExeption("error"));

        assertThrows(TaskMenegmentExeption.class, () -> usersService.login(authRequest, request));
    }
}