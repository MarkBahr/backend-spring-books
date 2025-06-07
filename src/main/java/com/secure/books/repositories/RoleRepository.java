package com.secure.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.secure.books.models.AppRole;
import com.secure.books.models.Role;

import java.util.Optional;

// This interface handles database persistence for roles using Spring Data Jpa/Hibernate
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // findBy<column> is a derived query
    Optional<Role> findByRoleName(AppRole appRole);
}