package com.furkhan.smartexpensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.furkhan.smartexpensetracker.dto.LoginRequest;
import com.furkhan.smartexpensetracker.dto.RegisterRequest;
import com.furkhan.smartexpensetracker.response.ApiResponse;
import com.furkhan.smartexpensetracker.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest request) {

        return userService.register(request);
    }
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}