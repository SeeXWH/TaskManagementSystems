package com.example.Task_Management_Systems.service;

import com.example.Task_Management_Systems.config.MyUserDetails;
import com.example.Task_Management_Systems.exeption.TaskMenegmentExeption;
import com.example.Task_Management_Systems.model.Task;
import com.example.Task_Management_Systems.model.Users;
import com.example.Task_Management_Systems.repository.UsersRepository;
import com.example.Task_Management_Systems.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository repository;
    private JwtTokenProvider jwtTokenProvider;

    public MyUserDetailsService(UsersRepository repository) {
        this.repository = repository;
    }

    public MyUserDetailsService() {
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = Optional.ofNullable(repository.findByUsername(username));
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "Not found"));
    }



}
