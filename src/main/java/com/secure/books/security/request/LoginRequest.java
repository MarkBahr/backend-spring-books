package com.secure.books.security.request;

import lombok.Getter;
import lombok.Setter;

/**
 * This class provides the payload (data) for a login http request
 */
@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
}