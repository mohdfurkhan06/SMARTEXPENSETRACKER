package com.furkhan.smartexpensetracker.controller;

import com.furkhan.smartexpensetracker.dto.ProfileResponse;
import com.furkhan.smartexpensetracker.dto.UpdateProfileRequest;
import com.furkhan.smartexpensetracker.response.ApiResponse;
import com.furkhan.smartexpensetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<ProfileResponse> getProfile(){

        return new ApiResponse<>(
                true,
                "Success",
                userService.getProfile()
        );

    }

    @PutMapping
    public ApiResponse<String> updateProfile(
            @Valid
            @RequestBody
            UpdateProfileRequest request){

        return new ApiResponse<>(
                true,
                userService.updateProfile(request),
                null
        );

    }

}