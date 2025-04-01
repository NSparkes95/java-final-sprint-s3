package org.keyin.user;

import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for handling user-related operations such as registration, login, update, and deletion.
 */
public class UserService {
    private UserDao userDao;

    // Constructor
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // Method to register a new user
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

    // Login method
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

    // View all users (for admin)
    public void viewAllUsers() throws SQLException {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
            System.out.println("✅ User updated successfully: " + user.getUserName());
        } catch (Exception e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
        }
    }

    // Delete user by ID (for admin)
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
