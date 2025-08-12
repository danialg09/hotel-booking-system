package com.hotel.service;

import com.hotel.entity.Role;
import com.hotel.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByName(String name);
    User findByNameAndEmail(String name, String email);
    User save(User user, Role role);
    User update(User user);
    void delete(Long id);
}
