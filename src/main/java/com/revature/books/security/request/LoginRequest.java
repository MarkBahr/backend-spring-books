package com.revature.books.security.request;

import lombok.Setter;
import lombok.Getter;

/**
 * This class provides the payload (data) for a login http request
 */
@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
}