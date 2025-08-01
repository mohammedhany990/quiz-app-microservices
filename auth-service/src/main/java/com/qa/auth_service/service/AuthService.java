package com.qa.auth_service.service;

import com.qa.auth_service.dto.LoginDto;
import com.qa.auth_service.dto.RegisterDto;
import com.qa.auth_service.dto.UserDto;
import com.qa.auth_service.entity.User;
import com.qa.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDto register(RegisterDto dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already in use");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .role(dto.getRole() == null ?   "ROLE_USER": dto.getRole())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        user = userRepo.save(user);

        String token = jwtService.generateToken(user);

        return UserDto.builder()
                .username(user.getUsername())
                .token(token)
                .build();

    }

    public UserDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        User user = userRepo.findByUsername(dto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String token = jwtService.generateToken(user);

        return UserDto.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }


}
