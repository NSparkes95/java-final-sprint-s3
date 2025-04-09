package org.keyin.user;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String email) {
        return userDao.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean registerUser(String username, String email, String password, String role) {
        return userDao.registerUser(username, email, password, role);
    }
}
