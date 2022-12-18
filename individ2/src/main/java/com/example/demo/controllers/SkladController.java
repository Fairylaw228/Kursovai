package com.example.demo.controllers;


import com.example.demo.models.Tovaradd;
import com.example.demo.models.User;
import com.example.demo.repo.TovarRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class SkladController {


    @Autowired
    private TovarRepository tovarRepository;

    private final UserRepository userRepository;

    public SkladController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }








}
