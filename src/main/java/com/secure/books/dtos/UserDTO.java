package com.secure.books.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.time.LocalDate;
// import java.time.LocalDateTime;

import com.secure.books.models.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private Role role;
    // private boolean accountNonLocked;
    // private boolean accountNonExpired;
    // private boolean credentialsNonExpired;
    // private boolean enabled;
    // private LocalDate credentialsExpiryDate;
    // private LocalDate accountExpiryDate;
    // private String twoFactorSecret;
    // private boolean isTwoFactorEnabled;
    // private String signUpMethod;
    // private LocalDateTime createdDate;
    // private LocalDateTime updatedDate;
}
