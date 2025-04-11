// UserDaoImpl.java
package org.keyin.user;

import org.keyin.database.DatabaseConnection;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.utils.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserDao for handling user-related database operations.
 * Supports login, registration with password hashing, and user listing.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Attempts to find a user by email and validate their password interactively.
     * If email is not found or password is incorrect, returns null.
     * @param email the email of the user attempting to log in
     * @return User object if valid, null otherwise
     */
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             java.util.Scanner scanner = new java.util.Scanner(System.in)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("user_role");
                int id = rs.getInt("user_id");
                String username = rs.getString("user_name");
                String hashedPassword = rs.getString("user_password");

                System.out.print("Enter password: ");
                String enteredPassword = scanner.nextLine();

                if (!PasswordUtils.checkPassword(enteredPassword, hashedPassword)) {
                    System.out.println("Incorrect password.");
                    return null;
                }

                switch (role.toLowerCase()) {
                    case "admin":
                        return new Admin(id, username, email, hashedPassword);
                    case "trainer":
                        return new Trainer(id, username, email, hashedPassword);
                    case "member":
                        return new Member(id, username, email, hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     * @return a list of all User objects
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String role = rs.getString("user_role");
                int id = rs.getInt("user_id");
                String username = rs.getString("user_name");
                String email = rs.getString("user_email");
                String password = rs.getString("user_password");

                switch (role.toLowerCase()) {
                    case "admin":
                        users.add(new Admin(id, username, email, password));
                        break;
                    case "trainer":
                        users.add(new Trainer(id, username, email, password));
                        break;
                    case "member":
                        users.add(new Member(id, username, email, password));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Registers a new user in the database with hashed password.
     * @param username the new user's username
     * @param email the new user's email
     * @param password the new user's plain password
     * @param role the assigned role (admin, trainer, member)
     * @return true if insert was successful, false otherwise
     */
    @Override
    public boolean registerUser(String username, String email, String password, String role) {
        String sql = "INSERT INTO users (user_name, user_email, user_password, user_role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, PasswordUtils.hashPassword(password));
            stmt.setString(4, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
} 
