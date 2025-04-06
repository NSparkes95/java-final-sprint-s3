package org.keyin.user;

import java.util.List; 
import org.keyin.utils.PasswordUtils;
import java.sql.SQLException;

/**
 * Service class for handling user-related operations such as registration, login, update, and deletion.
 * This class uses the UserDao to interact with the database and perform CRUD operations on users.
 */
public class UserService {
    private final UserDao userDao;

    /**
     * Constructor to initialize the UserService with a UserDao.
     * 
     * @param userDao The UserDao implementation to use for database interactions.
     */
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user by hashing the password and saving the user based on their role.
     * 
     * @param username The username for the new user.
     * @param plainPassword The plain text password for the new user.
     * @param email The email for the new user.
     * @param phone The phone number for the new user.
     * @param address The address for the new user.
     * @param role The role of the new user (Admin, Member, Trainer).
     * @throws SQLException If a database access error occurs during user registration.
     */
    public void registerUser(String username, String plainPassword, String email, String phone, String address, String role) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        // Create a User object with the given role
        User user = new User(username, hashedPassword, email, phone, address, role);

        // Save the user to the database
        userDao.insertUser(user);
        System.out.println("✅ User registered successfully: " + user.getUserName());
    }

    /**
     * Logs in a user by verifying their username and password.
     * 
     * @param username The username of the user trying to log in.
     * @param plainPassword The plain text password provided by the user.
     * @return The User object if login is successful, null otherwise.
     * @throws SQLException If a database access error occurs during the login process.
     */
    public User login(String username, String plainPassword) throws SQLException {
        User user = userDao.getUserByUsername(username);

        // Check if the user exists and verify the password
        if (user != null && PasswordUtils.checkPassword(plainPassword, user.getPassword())) {
            System.out.println("✅ Login successful for user: " + username);
            return user;
        } else {
            System.out.println("❌ Invalid username or password.");
            return null;
        }
    }

    /**
     * Checks if the username already exists in the system.
     *
     * @param username The username to check.
     * @return true if the username exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean isUsernameTaken(String username) throws SQLException {
        return userDao.getUserByUsername(username) != null;
    }

    /**
     * Checks if the email already exists in the system.
     *
     * @param email The email to check.
     * @return true if the email exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean isEmailTaken(String email) throws SQLException {
        return userDao.getUserByEmail(email) != null;
    }

    /**
     * Displays a list of all users for an admin to view.
     * 
     * @throws SQLException If a database access error occurs.
     */
    public void viewAllUsers() throws SQLException {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * Updates an existing user's details in the database.
     * 
     * @param user The user object containing updated information.
     */
    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
            System.out.println("✅ User updated successfully: " + user.getUserName());
        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
        }
    }

    /**
     * Deletes a user from the database by their user ID.
     * 
     * @param userId The ID of the user to delete.
     */
    public void deleteUserById(int userId) {
        try {
            User user = userDao.getUserById(userId);
            if (user != null) {
                userDao.deleteUser(userId);
                System.out.println("✅ User deleted successfully: " + user.getUserName());
            } else {
                System.out.println("❌ User not found with ID: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
}
