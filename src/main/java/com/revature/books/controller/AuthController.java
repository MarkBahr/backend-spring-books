package com.revature.books.controller;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.books.repository.RoleRepository;
import com.revature.books.repository.UserRepository;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


/**
 * Class responsible for handling user authentication and authorization.
 * Entry point for requests related to user login and registration
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    UserRepository userRepostitory;  // Access to JPA queries for database persistence for users
    RoleRepository roleRepository;  // Same as above for roles
    AuthenticationManager authenticationManager; // For authenticating user credentials
    PasswordEncoder encoder; // For hashing and verifying passwords
    // JwtUtils ;


    
    public AuthController() {

    }

}
