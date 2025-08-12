package com.hotel.service.impl;

import com.hotel.entity.Role;
import com.hotel.entity.User;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.repository.UserRepository;
import com.hotel.service.UserService;
import com.hotel.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + id)
        );
    }

    @Override
    public User findByName(String name) {
        return repository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("User not found with name: " + name)
        );
    }

    @Override
    public User findByNameAndEmail(String name, String email) {
        return repository.findByNameAndEmail(name, email).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format
                        ("User not found with name: {0}, email: {1}", name, email))
        );
    }

    @Override
    public User save(User user, Role role) {
        user = repository.save(user);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User existed = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existed);
        existed = repository.save(existed);
        existed.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(existed);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
