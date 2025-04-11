// Admin.java
package org.keyin.user.childclasses;

import org.keyin.user.User;

/**
 * Represents an Admin user in the system.
 * Inherits basic user fields and behaviors from the User superclass.
 * Admins have permissions to view users and revenue.
 */
public class Admin extends User {

    /**
     * Constructs an Admin user with required user information.
     *
     * @param id        Unique identifier for the admin user
     * @param username  The admin's display name
     * @param email     The admin's email address (used for login)
     * @param password  The admin's hashed password
     */
    public Admin(int id, String username, String email, String password) {
        super(id, username, email, password);
    }
}
