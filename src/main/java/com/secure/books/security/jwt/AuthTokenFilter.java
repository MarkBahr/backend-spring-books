package com.secure.books.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.secure.books.security.services.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * This class filters incoming requests to check for valid JWT in the header, setting the authentication context if the token is valid. It intercepts every request the server gets, looks for the JWT token
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * This method gives access to the request and response object, intercepts each requests, checks for valid tokens,
     * and authenticates the user based on the token. The method gives access to protected resources.
     * To do its work, this class uses methods from our JwtUtils class.
     * This method overrides a method from OncePerRequestFilter, so the logic is executed only once per request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());
        try {
            // Get the jwt from the request using parseJwt() method defined below
            String jwt = parseJwt(request);

            // If JWT not null and is valid
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Get username
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create UsernamePasswordAuthenticationToken object & pass roles for that user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());
                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());

                // Set and build details for authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Update security context (Tell it the user is authenticated)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        // continue chain of other filters
        filterChain.doFilter(request, response);
    }

    /**
     * This method uses a method from the JwtUtils class to return the jwt token.
     * @param request
     * @return String token
     */
    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        logger.debug("AuthTokenFilter.java: {}", jwt);
        return jwt;
    }
}
