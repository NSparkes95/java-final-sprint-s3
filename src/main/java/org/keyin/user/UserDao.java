// UserDao.java
package org.keyin.user;

import java.util.List;

/**
 * UserDao defines the contract for user-related database operations.
 * Implementing classes should provide logic to fetch, register, and list users.
 */
public interface UserDao {

    /**
     * Finds a user by their email address for login purposes.
     * @param email the email address to search for
     * @return the matching User object or null if not found
     */
    User findByEmail(String email);

    /**
     * Retrieves a list of all users in the database.
     * @return list of all User objects
     */
    List<User> getAllUsers();

    /**
     * Registers a new user in the database.
     * @param username the username of the new user
     * @param email the email address of the new user
     * @param password the plain text password (will be hashed)
     * @param role the role assigned to the user (admin, member, trainer)
     * @return true if registration is successful, false otherwise
     */
    boolean registerUser(String username, String email, String password, String role);
} 
