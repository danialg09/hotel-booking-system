package com.hotel.web.controller;

import com.hotel.exception.AlreadyExistsException;
import com.hotel.repository.UserRepository;
import com.hotel.service.impl.AuthService;
import com.hotel.web.dto.auth.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Username : " + userDetails.getUsername()
                + "Role : " + userDetails.getAuthorities();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.existsByName(registerRequest.getUsername())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Username: {0} already exists", registerRequest.getUsername())
            );
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Email: {0} already exists", registerRequest.getEmail())
            );
        }
        authService.register(registerRequest);

        return "User registered successfully";
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/log-out")
    @ResponseStatus(HttpStatus.OK)
    public String logout(@AuthenticationPrincipal UserDetails userDetails) {
        authService.logOut();
        return MessageFormat.format(
                        "Logged out successfully, username: {0}",userDetails.getUsername());
    }
}
