// Trainer.java
package org.keyin.user.childclasses;

import org.keyin.user.User;

/**
 * Represents a Trainer user in the gym system.
 * Trainers are responsible for managing workout classes and may also have memberships.
 */
public class Trainer extends User {

    /**
     * Constructs a Trainer with login and identification info.
     *
     * @param id        The unique identifier for the trainer
     * @param username  The trainer's display name
     * @param email     Email used for authentication
     * @param password  Hashed password for secure access
     */
    public Trainer(int id, String username, String email, String password) {
        super(id, username, email, password);
    }
}
