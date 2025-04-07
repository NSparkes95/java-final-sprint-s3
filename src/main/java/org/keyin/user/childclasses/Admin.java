package org.keyin.user.childclasses;

import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    /**
     * Constructor for the Admin class.
     * 
     * @param username The username of the admin.
     * @param password The password of the admin (hashed).
     * @param email    The email address of the admin.
     * @param phone    The phone number of the admin.
     * @param address  The address of the admin.
     */
    public Admin(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address, "Admin");
    }

    /**
     * Allows the admin to view all trainers.
     * 
     * @param userService The UserService to interact with user-related data.
     */
    public void viewAllTrainers(UserService userService) {
        try {
            List<User> trainers = userService.getAllTrainers();
            if (trainers.isEmpty()) {
                System.out.println("No trainers found.");
            } else {
                System.out.println("\n📋 All Trainers:");
                for (User t : trainers) {
                    System.out.println(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving trainers: " + e.getMessage());
        }
    }

    /**
     * Allows the admin to add a new trainer.
     * 
     * @param scanner     Scanner for input.
     * @param userService The UserService to interact with user-related data.
     */
    public void addNewTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer username: ");
        String username = scanner.nextLine();

        if (userService.isUsernameTaken(username)) {
            System.out.println("❌ Username already exists.");
            return;
        }

        System.out.print("Enter trainer email: ");
        String email = scanner.nextLine();

        if (userService.isEmailTaken(email)) {
            System.out.println("❌ Email already exists.");
            return;
        }

        System.out.print("Enter trainer password: ");
        String password = scanner.nextLine();

        if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            System.out.println("❌ Password must be at least 8 characters, include a digit and a special character.");
            return;
        }

        User trainer = new User(username, password, email, "Trainer");
        try {
            userService.registerUser(trainer);
            System.out.println("✅ Trainer added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding trainer: " + e.getMessage());
        }
    }

    /**
     * Allows the admin to update a trainer's details.
     * 
     * @param scanner     Scanner for input.
     * @param userService The UserService to interact with user-related data.
     */
    public void updateTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer ID to update: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null) {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Enter new username (or press Enter to keep current): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) trainer.setUserName(newUsername);

            System.out.print("Enter new email (or press Enter to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) trainer.setEmail(newEmail);

            System.out.print("Enter new password (or press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) trainer.setPassword(newPassword);

            userService.updateUser(trainer);
            System.out.println("✅ Trainer updated successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating trainer: " + e.getMessage());
        }
    }

    /**
     * Allows the admin to delete a trainer by ID.
     * 
     * @param scanner     Scanner for input.
     * @param userService The UserService to interact with user-related data.
     */
    public void deleteTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer ID to delete: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null || !trainer.getRole().equalsIgnoreCase("Trainer")) {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this trainer? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                userService.deleteUser(trainerId);
                System.out.println("✅ Trainer deleted successfully.");
            } else {
                System.out.println("❌ Trainer deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting trainer: " + e.getMessage());
        }
    }

    /**
     * Allows the admin to delete a workout class.
     * 
     * @param scanner        Scanner for input.
     * @param workoutService The WorkoutClassService to interact with workout class data.
     */
    public void deleteWorkoutClass(Scanner scanner, WorkoutClassService workoutService) {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());

        try {
            WorkoutClass workoutClass = workoutService.getWorkoutClassById(classId);
            if (workoutClass == null) {
                System.out.println("❌ Class not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this class? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                workoutService.deleteWorkoutClass(classId);
                System.out.println("✅ Class deleted successfully.");
            } else {
                System.out.println("❌ Class deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting class: " + e.getMessage());
        }
    }
}
