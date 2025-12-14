package com.hotel.service.impl;

import com.hotel.entity.RefreshToken;
import com.hotel.entity.User;
import com.hotel.exception.RefreshTokenException;
import com.hotel.repository.UserRepository;
import com.hotel.security.AppUserDetails;
import com.hotel.security.jwt.JwtUtils;
import com.hotel.web.dto.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.save(userDetails.getId());

        return LoginResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .token(jwtUtils.generateToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @Transactional
    public void register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.setRoles(request.getRoles());
        userRepository.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        return refreshTokenService.findByRefreshToken(refreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    User tokenUser = userRepository.findById(userId).orElseThrow(
                            () -> new RefreshTokenException(MessageFormat
                                    .format("Exception of trying to get refresh token from userId: {0}", userId)));
                    String token = jwtUtils.generateTokenFromUsername(tokenUser.getName());
                    return new RefreshTokenResponse(token, refreshTokenService.save(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(refreshToken, "Refresh token not found"));
    }

    public void logOut() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            Long userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }
}
