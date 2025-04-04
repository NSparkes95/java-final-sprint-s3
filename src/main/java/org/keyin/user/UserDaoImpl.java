package org.keyin.user;

import org.keyin.database.DatabaseConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Implements the UserDao interface and handles database operations using JDBC.
 * This class interacts with the users table in the database to perform CRUD operations on users.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Inserts a new user into the database.
     * 
     * @param user The User object to insert into the database.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (user_name, user_password, user_email, user_phone, user_address, user_role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a user by their username from the database.
     * 
     * @param username The username of the user to retrieve.
     * @return The User object corresponding to the username, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getString("user_phone"),
                            rs.getString("user_address"),
                            rs.getString("user_role")
                    );
                }
            }
        }

        return null; // User not found
    }

    /**
     * Retrieves a user by their user ID from the database.
     * 
     * @param userId The ID of the user to retrieve.
     * @return The User object corresponding to the user ID, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getString("user_phone"),
                            rs.getString("user_address"),
                            rs.getString("user_role")
                    );
                }
            }
        }

        return null; // User not found
    }

    /**
     * Retrieves all users in the system from the database.
     * 
     * @return A list of all User objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_email"),
                        rs.getString("user_phone"),
                        rs.getString("user_address"),
                        rs.getString("user_role")
                );
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Updates an existing user's information in the database.
     * 
     * @param user The User object containing updated information.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, user_password = ?, user_email = ?, user_phone = ?, user_address = ?, user_role = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, user.getUserId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     * 
     * @param userId The ID of the user to delete.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
