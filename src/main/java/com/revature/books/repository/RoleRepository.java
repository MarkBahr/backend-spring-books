package com.revature.books.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.books.model.ERole;
import com.revature.books.model.Role;

// This interface handles database persistence for roles using Spring Data Jpa/Hibernate
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    // findBy<column> is a derived query
    Optional<Role> findByRoleName(ERole name);
}