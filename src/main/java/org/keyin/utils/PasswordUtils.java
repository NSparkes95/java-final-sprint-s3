package org.keyin.utils;

import org.mindrot.jbcrypt.Bcrypt;

public class PasswordUtils {
    // Hash a plain text password
    public static String hashPassword(String plainTextPassword) {
        return Bcrypt.hashpw(plainTextPassword, Bcrypt.gensalt(12));
    }

    // Check if a plain text password matches a hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return Bcrypt.checkpw(plainTextPassword, hashedPassword);
    }
}