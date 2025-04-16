// UserService.java
package org.keyin.user;

import java.util.List;

/**
 * Service layer class that handles user-related operations,
 * including registration, login, retrieval, and deletion of users.
 */
public class UserService {
    private final UserDao userDao;

    /**
     * Constructs a UserService with the provided UserDao.
     * @param userDao the DAO implementation used for user persistence operations
     */
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user in the system.
     * @param username the username of the new user
     * @param email the email of the new user
     * @param password the password of the new user
     * @param role the role of the new user (e.g., "admin", "user")
     * @param phone the phone number of the new user
     * @param address the address of the new user
     * @return true if registration was successful, false otherwise
     */
    public boolean registerUser(String username, String email, String password, String role, String phone, String address) {
        return userDao.registerUser(username, email, password, role, phone, address);
    }

    /**
     * Attempts to log in a user by their email.
     * Delegates authentication to the DAO.
     * @param email user's email
     * @return the corresponding User object if login is valid, otherwise null
     */
    public User login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    /**
     * Retrieves a list of all users in the system.
     * @return list of all registered users
     */
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to be deleted
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        return userDao.deleteUserById(userId);
    }
    
} 
