// Member.java
package org.keyin.user.childclasses;

import org.keyin.user.User;

/**
 * Represents a gym member in the system.
 * Inherits all common user attributes and behavior.
 * Members can view classes, buy memberships, and track their own subscriptions.
 */
public class Member extends User {

    /**
     * Constructs a Member user with essential login and identity details.
     *
     * @param id        Unique ID of the member
     * @param username  Display name of the member
     * @param email     Member's email address
     * @param password  Hashed password for authentication
     */
    public Member(int id, String username, String email, String password) {
        super(id, username, email, password);
        super.setRole("member");
    }
}
