package com.revature.books.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.books.models.ERole;
import com.revature.books.models.Role;

// This interface handles database persistence for roles using Spring Data Jpa/Hibernate
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    // findBy<column> is a derived query
    Optional<Role> findByName(ERole name);
}