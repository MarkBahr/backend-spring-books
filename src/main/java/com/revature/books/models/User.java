package com.revature.books.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data // Takes care of equals() and hashCode for this class
@Entity // Maps this java entity to the users table in the database
@Table(name = "users", 
    // Specifies that unique constraints exists in the database
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {

    @Id // This is the primary key column in the users table
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing value
    private Long userId;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    
    @ManyToMany(fetch = FetchType.LAZY)
    // The name of the joining table is "user_roles"
    @JoinTable(  name = "user_roles",
        // Foreign key in the user_roles table for users is user_id
        joinColumns = @JoinColumn(name = "user_id"),
        // Foreign key in the user_roles table for roles is role_id
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    // Set of Role entities associated with a User (as seen in Enum model)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
