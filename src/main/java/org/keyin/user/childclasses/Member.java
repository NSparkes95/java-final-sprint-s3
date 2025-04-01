package org.keyin.user.childclasses;

import org.keyin.user.User;

public class Member extends User {
    public Member(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "member");
    }
}
