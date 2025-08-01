package com.qa.auth_service.controller;

import com.qa.auth_service.dto.LoginDto;
import com.qa.auth_service.dto.RegisterDto;
import com.qa.auth_service.dto.UserDto;
import com.qa.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto request) {
        UserDto response = authService.register(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto request) {
        UserDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}