package org.keyin.user;

import java.util.List;
java.util.SQLException;

public interface UserDao {
    User getUserByUsername(String username) throws SqlException;
    void insertUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void deleteUser(int userId) throws SQLException;
    void updateUser(User user) throws SQLException;
}