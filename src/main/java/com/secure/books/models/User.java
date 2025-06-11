package com.secure.books.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

// // import java.time.LocalDate;
// import java.time.LocalDateTime;

@Data // Takes care of equals() and hashCode 1 setter & 1 getter for this class
@Entity // Maps this java entity to the users table in the database
@NoArgsConstructor
@Table(name = "users",
        // Specifies that unique constraints exists in the database
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
    })
public class User{
    @Id // This is the primary key column in the users table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing value
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @Size(max = 120)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    // private boolean accountNonLocked = true;
    // private boolean accountNonExpired = true;
    // private boolean credentialsNonExpired = true;
    // private boolean enabled = true;

    // private LocalDate credentialsExpiryDate;
    // private LocalDate accountExpiryDate;

    // private String twoFactorSecret;
    // private boolean isTwoFactorEnabled = false;
    // private String signUpMethod;

    // Many to many relationship between roles and users
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    // Join with roles on role_id
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    @ToString.Exclude
    private Role role;

    // @CreationTimestamp
    // @Column(updatable = false)
    // private LocalDateTime createdDate;

    // @UpdateTimestamp
    // private LocalDateTime updatedDate;

    // Constructor for user signup
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // Constructor for login
    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return userId != null && userId.equals(((User) o).getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


