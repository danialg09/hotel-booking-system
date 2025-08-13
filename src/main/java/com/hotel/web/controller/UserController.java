package com.hotel.web.controller;

import com.hotel.entity.Role;
import com.hotel.entity.RoleType;
import com.hotel.entity.User;
import com.hotel.mapper.UserMapper;
import com.hotel.security.AppUserPrincipal;
import com.hotel.service.UserService;
import com.hotel.web.dto.user.UserListResponse;
import com.hotel.web.dto.user.UserRequest;
import com.hotel.web.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserListResponse findAll() {
        return mapper.userListToUserListResponse(service.findAll());
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String profile(@AuthenticationPrincipal AppUserPrincipal user) {
        return MessageFormat.format("Method called by user: {0}, Role is: {1}",
                user.getUsername(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserResponse findById(@PathVariable Long id) {
        return mapper.userToResponse(service.findById(id));
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest request, @RequestParam RoleType roleType) {
        User user = service.save(mapper.requestToUser(request), Role.fromAuthority(roleType));
        return mapper.userToResponse(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        User updated = service.update(mapper.requestToUser(id, request));
        return mapper.userToResponse(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
