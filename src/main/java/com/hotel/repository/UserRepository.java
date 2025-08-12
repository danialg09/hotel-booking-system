package com.hotel.repository;

import com.hotel.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByName(String name);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByNameAndEmail(String name, String email);
}
