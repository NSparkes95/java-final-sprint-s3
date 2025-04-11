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
     * Constructor for creating a new user with only username and password.
     */
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", role=" + role + "]";
    }
}
