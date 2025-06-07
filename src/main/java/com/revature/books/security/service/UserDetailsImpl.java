package com.revature.books.security.service;

// import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;

import com.revature.books.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is a link between our custom user data and Spring Security's UserDetails auth mechanisms
 * It encapsulates the info necessary for Spring Security to authenticate and authorize users
 */
@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    // Fields to represent custom user data from our user model
    private Long userId;
    private String username;
    private String email;

    @JsonIgnore // Don't include password in JSON object output
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Convert the User object to a UserDetailsImpl object, which maps the user roles to UserDetailsImpl object
    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName().name());

        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                List.of(authority)
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    // the overridden methods below come from UserDetails
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}