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
        String sql = "INSERT INTO users (username, userpassword, useremail, userphone, useraddress, userrole) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, user.getRole());

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
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("userpassword"),
                            rs.getString("useremail"),
                            rs.getString("userphone"),
                            rs.getString("useraddress"),
                            rs.getString("userrole")
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
        String sql = "SELECT * FROM users WHERE userid = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("userpassword"),
                            rs.getString("useremail"),
                            rs.getString("userphone"),
                            rs.getString("useraddress"),
                            rs.getString("userrole")
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
                        rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("userpassword"),
                        rs.getString("useremail"),
                        rs.getString("userphone"),
                        rs.getString("useraddress"),
                        rs.getString("userrole")
                );
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Retrieves all trainers from the database.
     * 
     * @return A list of User objects representing trainers.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<User> getAllTrainers() throws SQLException {
        List<User> trainers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'trainer'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) trainers.add(extractUser(rs));
        }
        return trainers;
    }

    /**
     * Updates an existing user's information in the database.
     * 
     * @param user The User object containing updated information.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, userpassword = ?, useremail = ?, userphone = ?, useraddress = ?, userrole = ? WHERE userid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, user.getRole());
            pstmt.setInt(7, user.getUserId());

            stmt.executeUpdate();
        }
    }

     /**
     * Retrieves a user by their email.
     * 
     * @param email The email of the user.
     * @return The User object corresponding to the email, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("userid"),
                            rs.getString("username"),
                            rs.getString("userpassword"),
                            rs.getString("useremail"),
                            rs.getString("userphone"),
                            rs.getString("useraddress"),
                            rs.getString("userrole")
                    );
                }
            }
        }

        return null; // User not found
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

    /**
     * Checks if an email is already taken by another user.
     * 
     * @param email The email to check.
     * @return true if the email is taken, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public boolean isUsernameTaken(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    /**
     * Checks if an email is already taken by another user.
     * 
     * @param email The email to check.
     * @return true if the email is taken, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public boolean isEmailTaken(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    /**
     * Helper method to extract a User object from a ResultSet.
     * 
     * @param rs The ResultSet containing user data.
     * @return A User object populated with data from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userid"));
        user.setUserName(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setUserRole(rs.getString("role"));
        return user;
    }
}
