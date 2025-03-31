package org.keyin.user;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getUserByUsername(String username) throws SQLException;
    void insertUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void deleteUser(int userId) throws SQLException;
    void updateUser(User user) throws SQLException;
}
