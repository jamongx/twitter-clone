package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        logger.info("Registering user: {}", registerDto);
        System.out.println("registerDto=" +registerDto);
        String response = authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}

