package com.secure.books.security.response;

// import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * This class defines responses pertaining to user information. To get details of authenticated users
 */
@Setter
@Getter
public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    // private boolean accountNonLocked;
    // private boolean accountNonExpired;
    // private boolean credentialsNonExpired;
    // private boolean enabled;
    // private LocalDate credentialsExpiryDate;
    // private LocalDate accountExpiryDate;
    // private boolean isTwoFactorEnabled;

    public UserInfoResponse(Long id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        // this.accountNonLocked = accountNonLocked;
        // this.accountNonExpired = accountNonExpired;
        // this.credentialsNonExpired = credentialsNonExpired;
        // this.enabled = enabled;
        // this.credentialsExpiryDate = credentialsExpiryDate;
        // this.accountExpiryDate = accountExpiryDate;
        // this.isTwoFactorEnabled = isTwoFactorEnabled;
    }
}
