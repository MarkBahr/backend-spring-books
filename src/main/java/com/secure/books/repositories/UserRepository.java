package com.secure.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secure.books.models.User;

import java.util.Optional;

// This interface handles database persistence for Users using JPA/Hibernate
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Returns a user object or null
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}

