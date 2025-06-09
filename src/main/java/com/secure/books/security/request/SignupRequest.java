package com.secure.books.security.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This class explicitly defines the signup request
 */
@Data
public class SignupRequest {
    @NotBlank // Cannot be blank
    @Size(min = 3, max = 20) // Validation lenght of username must be 3-20 characters
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email // String has to be a well-formed email address.
    private String email;

    @Setter
    @Getter
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
