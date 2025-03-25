package com.hanzai.app.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for password encryption and verification using BCrypt.
 */
public class PasswordUtil {

    // Use a single instance of BCryptPasswordEncoder to improve performance
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * Encrypts a raw password using BCrypt hashing.
     *
     * @param password The raw password to encrypt.
     * @return The hashed password.
     */
    public static String encode(String password) {
        return ENCODER.encode(password);
    }

    /**
     * Verifies if a raw password matches the encrypted password.
     *
     * @param rawPassword       The raw password entered by the user.
     * @param storedPassword The hashed password stored in the database.
     * @return true if the password matches, false otherwise.
     */
    public static boolean match(String rawPassword, String storedPassword) {
        return ENCODER.matches(rawPassword, storedPassword);
    }

}
