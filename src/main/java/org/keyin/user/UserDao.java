package org.keyin.user;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for User Data Access Object (DAO).
 * This interface defines the methods for interacting with the users table in the database.
 */
public interface UserDao {

    /**
     * Retrieves a user by their username.
     * 
     * @param username The username of the user.
     * @return The User object corresponding to the username, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    User getUserByUsername(String username) throws SQLException;

    /**
     * Retrieves a user by their user ID.
     * 
     * @param userId The ID of the user.
     * @return The User object corresponding to the user ID, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    User getUserById(int userId) throws SQLException;

    /**
     * Retrieves a user by their email.
     * 
     * @param email The email of the user.
     * @return The User object corresponding to the email, or null if no such user exists.
     * @throws SQLException If a database access error occurs.
     */
    User getUserByEmail(String email) throws SQLException;

    /**
     * Inserts a new user into the database.
     * 
     * @param user The User object to insert into the database.
     * @throws SQLException If a database access error occurs.
     */
    void insertUser(User user) throws SQLException;

    /**
     * Retrieves all users in the system.
     * 
     * @return A list of all User objects.
     * @throws SQLException If a database access error occurs.
     */
    List<User> getAllUsers() throws SQLException;

    /**
     * Deletes a user by their ID.
     * 
     * @param userId The ID of the user to delete.
     * @throws SQLException If a database access error occurs.
     */
    void deleteUser(int userId) throws SQLException;

    /**
     * Updates the information of an existing user in the database.
     * 
     * @param user The User object with updated information.
     * @throws SQLException If a database access error occurs.
     */
    void updateUser(User user) throws SQLException;
}
