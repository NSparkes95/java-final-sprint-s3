// PasswordUtils.java
package org.keyin.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing and validating user passwords.
 * Uses BCrypt for secure, salted password handling.
 */
public class PasswordUtils {

    /**
     * Hashes a plain-text password using BCrypt.
     *
     * @param plainTextPassword the password to hash
     * @return a securely hashed password string
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    /**
     * Verifies a plain-text password against a hashed version.
     *
     * @param plainTextPassword the raw password to check
     * @param hashedPassword    the stored hashed password
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
} 
