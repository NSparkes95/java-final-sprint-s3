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
    User findByEmailAndPassword(String email, String enteredPassword); 

    /**
     * Retrieves a list of all users in the database.
     * @return list of all User objects
     */
    List<User> getAllUsers();

    /**
     * Registers a new user in the database.
     * @param username the username of the new user
     * @param email the email of the new user
     * @param password the password of the new user
     * @param role the role of the new user (e.g., "admin", "user")
     * @param phone the phone number of the new user
     * @param address the address of the new user
     * @return true if registration was successful, false otherwise
     */
    boolean registerUser(String username, String email, String password, String role, String phone, String address);


    /**
     * Deletes a user from the database by their ID.
     * @param userId the ID of the user to be deleted
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteUserById(int userId);

} 
