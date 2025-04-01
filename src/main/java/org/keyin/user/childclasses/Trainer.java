package org.keyin.user.childclasses;

import org.keyin.user.User;

public class Trainer extends User {
    public Trainer(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "trainer");
    }
}
