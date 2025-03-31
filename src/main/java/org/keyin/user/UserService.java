package org.keyin.user;

import org.keyin.utils.PasswordUtils;
import org.keyin.user.User;
import org.keyin.user.UserDao;

public class UserService {
    private UserDao userDao;

    // Constructor
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // Method to register a new user
    public void registerUser(String username, String plainPassword, String email, String phone, String address, String role) {
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
                return;
        }

        // Save the user to the database
        userDao.insertUser(user);
        System.out.println("User registered successfully: " + user.getUsername());
    }

    // Login method
    public User Login(String username, String plainPassword) {
        User user = userDao.getUserByUsername(username);

        // Check if the user exists and verify the password
        if (user != null && PasswordUtils.verifyPassword(plainPassword, user.getPassword())) {
            System.out.println("Login successful for user: " + username);
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }

    // View all users (for admin)
    public void viewAllUsers() {
        List<User> users = userDao.getAllUsers();
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
    }

    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
            System.out.println("User updated successfully: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Delete user by ID (for admin)
    public void deleteUserById(int userId) {
        User user = userDao.getUserById(userId);
        if (user != null) {
            userDao.deleteUser(userId);
            System.out.println("User deleted successfully: " + user.getUsername());
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }
}
