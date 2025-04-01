package org.keyin.user.childclasses;

import org.keyin.user.User;

public class Admin extends User {
    public Admin(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "admin");
    }
}
