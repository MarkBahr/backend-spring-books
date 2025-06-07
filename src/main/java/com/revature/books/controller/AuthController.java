package com.revature.books.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// import com.revature.books.payload.request.*;
import com.revature.books.security.jwt.JwtUtils;
import com.revature.books.security.request.LoginRequest;
import com.revature.books.security.response.LoginResponse;
import com.revature.books.security.service.UserDetailsImpl;

/**
 * Class responsible for handling user authentication and authorization.
 * Entry point for requests related to user login and registration
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils; // from JwtUtils class
    
    @Autowired
    AuthenticationManager authenticationManager; // For authenticating user credentials


    /**
     * This method handles login requests
     * @param loginRequest - includes username and password
     * @return ResponseEntity with JwtResponse - includes JWT token, email, roles
     */
    @PostMapping("/public/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Create authentication object using username and password
        Authentication authentication;
        
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        // Set the securitycontext with authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
        
        // Get jwt token using userDetails object
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        
        // Get list of roles
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        // Prepare the response body, now including the JWT token directly in the body
        LoginResponse response = new LoginResponse(userDetails.getUsername(),
                roles, jwtToken);

        // Return Response Body with JWT response
        return ResponseEntity.ok(response);
    }


    /**
     * 
     * @param signUpRequest - Includes username, role, email & password
     * @return - ResponseEntity with message
     */
    // @PostMapping("/signup")
    //     public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    //         if (userRepository.existsByUsername(signUpRequest.getUsername())) {
    //         return ResponseEntity
    //             .badRequest()
    //             .body(new MessageResponse("Error: Username is already taken!"));
    //         }

    //     if (userRepository.existsByEmail(signUpRequest.getEmail())) {
    //     return ResponseEntity
    //         .badRequest()
    //         .body(new MessageResponse("Error: Email is already in use!"));
    //     }

    //     // Create new user's account
    //     User user = new User(signUpRequest.getUsername(), 
    //             signUpRequest.getEmail(),
    //             encoder.encode(signUpRequest.getPassword()));

    //     Set<String> strRoles = signUpRequest.getRole();
    //     Set<Role> roles = new HashSet<>();

    //     if (strRoles == null) {
    //     Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    //         .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //     roles.add(userRole);
    //     } else {
    //     strRoles.forEach(role -> {
    //         switch (role) {
    //         case "admin":
    //         Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
    //             .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //         roles.add(adminRole);

    //         break;
    //         case "mod":
    //         Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
    //             .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //         roles.add(modRole);

    //         break;
    //         default:
    //         Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    //             .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //         roles.add(userRole);
    //         }
    //     });
    //     }

    //     user.setRoles(roles);
    //     userRepository.save(user);

    //     return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    // }

}
