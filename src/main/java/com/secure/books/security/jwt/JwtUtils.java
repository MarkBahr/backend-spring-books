package com.secure.books.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Thic class makes sure that only authenticated users can access protected resources
 * It does this with a variety of utility methods to generate a token, extract the token
 * and username, and validate the JWT token. (methods that work with the token)
 */
@Component
public class JwtUtils {
    // Instance of logger
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Field for the JWT secret
    @Value("${spring.app.jwtSecret}") // Variable comes from application.properties
    private String jwtSecret;

    // JWT expiration time in milliseconds
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * This method extracts the JWT token value from the request header
     * It gets the request header, and if bearerToken startw with "Bearer " subString, it removes it.
     * @param request
     * @return String token or null
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix
        }
        return null;
    }

    /**
     * This method generates a token from our username and digitally signs the JWT to prevent tampering with its content.
     * It sets the expiration by adding the JWT expiration in milliseconds
     * @param userDetails
     * @return String token
     */
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    /**
     * Extract the username from the JWT token.
     * It uses parser and build to verify the token
     * @param token
     * @return String username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                        .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    // Generate a secret key from Base64 encoded string and get the value
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validates the JWT token with the help of the secret key that our application receives
     * @param authToken
     * @return true if the key is valid
     */
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
