package com.secure.books.security.services;

import java.util.Collection;
import java.util.List;
// import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secure.books.models.User;

/**
 * This class is a link between our custom user data and Spring Security's UserDetails auth mechanisms
 * It encapsulates the info necessary for Spring Security to authenticate and authorize users
 */
@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    // Fields to represent custom user data from our user model
    private Long id;
    private String username;
    private String email;

    @JsonIgnore // Don't include password in JSON object output
    private String password;

    private boolean is2faEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
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
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                List.of(authority) // Wrapping the single authority in a list
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
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

    // @Override
    // public boolean isAccountNonExpired() {
    //     return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    //     return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return true;
    // }

    // @Override
    // public boolean isEnabled() {
    //     return true;
    // }

    // public boolean is2faEnabled() {
    //     return is2faEnabled;
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o)
    //         return true;
    //     if (o == null || getClass() != o.getClass())
    //         return false;
    //     UserDetailsImpl user = (UserDetailsImpl) o;
    //     return Objects.equals(id, user.id);
    // }
}
