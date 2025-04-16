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
    public User findByEmailAndPassword(String email, String enteredPassword) {
        String sql = "SELECT * FROM users WHERE user_email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("user_role");
                int id = rs.getInt("user_id");
                String username = rs.getString("user_name");
                String hashedPassword = rs.getString("user_password");

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
            String phone = rs.getString("user_phone");
            String address = rs.getString("user_address");

            User user;
            switch (role.toLowerCase()) {
                case "admin":
                    user = new Admin(id, username, email, password);
                    break;
                case "trainer":
                    user = new Trainer(id, username, email, password);
                    break;
                case "member":
                    user = new Member(id, username, email, password);
                    break;
                default:
                    user = new User(id, username, email, password);
                    break;
            }

            user.setPhoneNumber(phone);
            user.setAddress(address);
            users.add(user);
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
     * @param phone the user's phone number
     * @param address the user's mailing address
     * @return true if insert was successful, false otherwise
     */
    @Override
    public boolean registerUser(String username, String email, String password, String role, String phone, String address) {
        String checkSql = "SELECT 1 FROM users WHERE user_email = ?";
        String insertSql = "INSERT INTO users (user_name, user_email, user_password, user_role, user_phone, user_address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Email exists
                return false;
            }   

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, PasswordUtils.hashPassword(password));
                insertStmt.setString(4, role);
                insertStmt.setString(5, phone);
                insertStmt.setString(6, address);
                insertStmt.executeUpdate();
                return true;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database by their ID.
     * @param userId the ID of the user to be deleted
     * @return true if deletion was successful, false otherwise
     */
    @Override
    public boolean deleteUserById(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setInt(1, userId);
          return stmt.executeUpdate() > 0;
     } catch (SQLException e) {
          System.out.println("Failed to delete user: " + e.getMessage());
           return false;
       }
    }

}
