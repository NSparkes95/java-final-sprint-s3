package org.keyin.user;

import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.utils.PasswordUtils;

import java.time.LocalDate;

/**
 * This is the parent class for all users.
 * There are 3 types of users: Trainer, Member, and Admin.
 * It contains user details like ID, name, password, email, phone, address, and role.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;

    /**
     * Full constructor (used by DAO when reading from the database).
     * 
     * @param userId    The unique ID of the user.
     * @param userName  The user's name.
     * @param password  The user's password (hashed).
     * @param email     The user's email address.
     * @param phone     The user's phone number.
     * @param address   The user's address.
     * @param role      The role of the user (Admin, Member, Trainer).
     */
    public User(int userId, String userName, String password, String email, String phone, String address, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    /**
     * Constructor used for registering new users (ID auto-generated).
     * 
     * @param userName  The user's name.
     * @param password  The user's password (plain text, to be hashed).
     * @param email     The user's email address.
     * @param phone     The user's phone number.
     * @param address   The user's address.
     * @param role      The role of the user (Admin, Member, Trainer).
     */
    public User(String userName, String password, String email, String phone, String address, String role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    /**
     * No-argument constructor for creating an empty User object.
     */
    public User() {
        // Default constructor for creating an empty User object
    }

    // Getters and setters

    /**
     * Gets the user ID.
     * 
     * @return The unique user ID.
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's name.
     * 
     * @return The user's name.
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password.
     * 
     * @return The user's password (hashed).
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email.
     * 
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number.
     * 
     * @return The user's phone number.
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the user's address.
     * 
     * @return The user's address.
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's role.
     * 
     * @return The role of the user (Admin, Member, Trainer).
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Set the user's role.
     * 
     * @param role The role to assign to the user.
     */
    public void setUserRole(String role) {
        this.role = role;
    }

    /**
     * Provides a string representation of the User object.
     * 
     * @return A string containing the user's ID, name, role, and email.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
