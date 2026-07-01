package com.furkhan.smartexpensetracker.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.furkhan.smartexpensetracker.dto.LoginRequest;
import com.furkhan.smartexpensetracker.dto.ProfileResponse;
import com.furkhan.smartexpensetracker.dto.RegisterRequest;
import com.furkhan.smartexpensetracker.dto.UpdateProfileRequest;
import com.furkhan.smartexpensetracker.entity.User;
import com.furkhan.smartexpensetracker.exception.UnauthorizedException;
import com.furkhan.smartexpensetracker.repository.UserRepository;
import com.furkhan.smartexpensetracker.response.ApiResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ApiResponse<String> register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new ApiResponse<>(true, "User registered successfully", null);
    }

    public ApiResponse<String> login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new ApiResponse<>(true, "Login successful", token);
    }
    public ProfileResponse getProfile()
    {
       String email = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

return new ProfileResponse(
        user.getName(),
        user.getEmail()
); 
    }
    public String updateProfile(UpdateProfileRequest request)
    {
        String email = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

user.setName(request.getName());

if(request.getPassword()!=null && !request.getPassword().isBlank()){

    user.setPassword(
        passwordEncoder.encode(request.getPassword())
    );

}

userRepository.save(user);

return "Profile updated successfully";
    }
}