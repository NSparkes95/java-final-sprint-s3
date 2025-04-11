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
     * Registers a new user using provided attributes.
     * @param username user's name
     * @param email user's email
     * @param password user's raw password (to be hashed)
     * @param role the assigned role (admin, trainer, member)
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(String username, String email, String password, String role) {
        return userDao.registerUser(username, email, password, role);
    }

    /**
     * Attempts to log in a user by their email.
     * Delegates authentication to the DAO.
     * @param email user's email
     * @return the corresponding User object if login is valid, otherwise null
     */
    public User login(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Retrieves a list of all users in the system.
     * @return list of all registered users
     */
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
} 
