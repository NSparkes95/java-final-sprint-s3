package org.keyin.user;

import org.keyin.database.DatabaseConnection;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.utils.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

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
                int id = rs.getInt("id");
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
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    @Override
    public boolean registerUser(String username, String email, String password, String role) {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
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
