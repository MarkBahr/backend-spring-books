package com.revature.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.books.models.User;
import java.util.Optional;

// This interface handles database persistence for Users using JPA/Hibernate
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // Returns a user object or null
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
