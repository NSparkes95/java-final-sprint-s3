package org.keyin.user;

/**
 * This is the parent class for all users.
 * There are 3 types of users: Trainer, Member, and Admin.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;

    // Full constructor (used by DAO when reading from DB)
    public User(int userId, String userName, String password, String email, String phone, String address, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    // Constructor used for registering new users (ID auto-generated)
    public User(String userName, String password, String email, String phone, String address, String role) {
        this(-1, userName, password, email, phone, address, role);
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    // Optional: override toString for display
    @Override
    public String toString() {
        return "User{" +
                "ID=" + userId +
                ", Name='" + userName + '\'' +
                ", Role='" + role + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }
}
