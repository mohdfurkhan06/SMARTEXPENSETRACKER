package com.furkhan.smartexpensetracker.config;

import com.furkhan.smartexpensetracker.security.JwtAuthenticationFilter;
import com.furkhan.smartexpensetracker.service.JwtService;
import com.furkhan.smartexpensetracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtService jwtService,
                                           UserService userService) throws Exception {

        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(jwtService, userService);

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}