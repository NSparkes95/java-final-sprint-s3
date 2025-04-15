// User.java
package org.keyin.user;

/**
 * The User class serves as the base class for all user roles in the system,
 * including Admin, Trainer, and Member. It contains shared user attributes
 * such as ID, username, email, password, phone number, address, and role.
 */
public class User {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String phoneNumber;
    protected String address;
    protected String role;

    /**
     * Full constructor for setting all user fields.
     *
     * @param id unique user ID
     * @param username chosen display name
     * @param password account password (should be stored securely)
     * @param email user email address
     * @param phoneNumber optional phone contact
     * @param address optional mailing address
     * @param role system role assigned (Admin, Member, Trainer)
     */
    public User(int id, String username, String password, String email, String phoneNumber, String address, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    /**
     * Minimal constructor used primarily during login flows.
     *
     * @param id user ID from database
     * @param username display name
     * @param email user email address
     * @param password account password
     */
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = "";
        this.address = "";
        this.role = "";
    }

    /**
     * Gets the user's ID.
     *
     * @return unique user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     *
     * @param id new ID to assign
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's display name.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's display name.
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the account password (not recommended for real-world use).
     *
     * @return plain text password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the account password.
     *
     * @param password new plain text password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number.
     *
     * @return phone contact string
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber new contact number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's mailing address.
     *
     * @return address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's mailing address.
     *
     * @param address new mailing address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's role (Admin, Trainer, Member).
     *
     * @return assigned role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's system role.
     *
     * @param role new role to assign
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a brief summary of the user.
     *
     * @return string representation showing ID, username, and role
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", role=" + role + "]";
    }
} 
