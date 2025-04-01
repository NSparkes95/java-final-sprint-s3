package org.keyin.user;

import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for handling user-related operations such as registration, login, update, and deletion.
 * This class uses the UserDao to interact with the database and perform CRUD operations on users.
 */
public class UserService {
    private UserDao userDao;

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
     * @param username    The username for the new user.
     * @param plainPassword The plain text password for the new user.
     * @param email       The email for the new user.
     * @param phone       The phone number for the new user.
     * @param address     The address for the new user.
     * @param role        The role of the new user (Admin, Member, Trainer).
     * @throws SQLException If a database access error occurs during user registration.
     */
    public void registerUser(String username, String plainPassword, String email, String phone, String address, String role) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        User user;

        // Determine role-based subclass to instantiate
        switch (role.toLowerCase()) {
            case "admin":
                user = new Admin(username, hashedPassword, email, phone, address);
                break;
            case "member":
                user = new Member(username, hashedPassword, email, phone, address);
                break;
            case "trainer":
                user = new Trainer(username, hashedPassword, email, phone, address);
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Save the user to the database
        userDao.insertUser(user);
        System.out.println("✅ User registered successfully: " + user.getUserName());
    }

    /**
     * Logs in a user by verifying their username and password.
     * 
     * @param username    The username of the user trying to log in.
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
        } catch (Exception e) {
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
            User user = userDao.getAllUsers()
                .stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst()
                .orElse(null);

            if (user != null) {
                userDao.deleteUser(userId);
                System.out.println("✅ User deleted successfully: " + user.getUserName());
            } else {
                System.out.println("❌ User not found with ID: " + userId);
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
}
