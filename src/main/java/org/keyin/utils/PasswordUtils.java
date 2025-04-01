package org.keyin.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Hash a plain text password
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    // Check if a plain text password matches a hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
