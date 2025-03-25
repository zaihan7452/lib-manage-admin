package com.hanzai.app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for JWT operations.
 * This class provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Slf4j
@Component
public class JwtUtil {

    // Secret key for signing the JWT token (base64-encoded)
    @Value("${jwt.secret}")
    private String secretKey;

    // Token expiration time (in milliseconds)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 60; // 24 hours

    /**
     * Generate a JWT token for the specified username.
     *
     * @param username the username for which the token is generated
     * @return the generated JWT token as a string
     */
    public String generateToken(String username) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, Jwts.SIG.HS256)  // Use updated API with algorithm
                .compact();
    }

    /**
     * Extract the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Validate the JWT token.
     *
     * @param token the JWT token to be validated
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Invalid token: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Extract the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    /**
     * Check if the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * Create a new JWT token with a refreshed expiration date.
     *
     * @param token the old JWT token
     * @return the new JWT token with updated expiration date
     */
    public String refreshToken(String token) {
        String username = getUsername(token);
        return generateToken(username);
    }

    /**
     * Extract the claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims in the token
     */
    public Claims getClaims(String token) {
        SecretKey key = getSigningKey();

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generate a SecretKey from the base64-encoded secret.
     *
     * @return the generated SecretKey
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
