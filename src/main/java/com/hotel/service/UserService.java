package com.hotel.service;

import com.hotel.entity.RoleType;
import com.hotel.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByName(String name);
    User findByNameAndEmail(String name, String email);
    User save(User user, RoleType role);
    User update(User user);
    void delete(Long id);
}
