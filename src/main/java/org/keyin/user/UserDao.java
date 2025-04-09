package org.keyin.user;

import java.util.List;

public interface UserDao {
    User findByEmail(String email);
    List<User> getAllUsers();
    boolean registerUser(String username, String email, String password, String role);
}
