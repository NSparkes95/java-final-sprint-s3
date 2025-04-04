package org.keyin;

import org.keyin.user.child_classes.Admin;
import org.keyin.user.child_classes.Member;
import org.keyin.user.child_classes.Trainer;
import org.keyin.membership.Membership;
import org.keyin.membership.MembershipService;
import org.keyin.membership.MembershipDAOImpl;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.user.UserDaoImpl;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;
import org.keyin.workoutclasses.WorkoutClassDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class GymApp {
    public static void main(String[] args) throws SQLException {
        // Initialize services with DAO injection
        UserService userService = new UserService(new UserDaoImpl());  // Inject UserDaoImpl into UserService
        MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
        WorkoutClassService workoutService = new WorkoutClassService(new WorkoutClassDAOImpl());  // Updated constructor with DAOImpl

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n🌟=== Welcome to the Gym Management System ===🌟");
            System.out.println("1. 🧑‍💻 Register New User");
            System.out.println("2. 🔐 Login");
            System.out.println("3. 🚪 Exit");
            System.out.print("Enter your choice: ");

            // Validate input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    logInAsUser(scanner, userService, membershipService, workoutService); // routes to role-specific menu
                    break;
                case 2:
                    addNewUser(scanner, userService);
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    // Handles user login and redirects to the appropriate menu based on role
    private static void logInAsUser(Scanner scanner, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            User user = userService.login(username, password);
            if (user != null) {
                System.out.println("Login Successful! Welcome " + user.getUserName());
                switch (user.getRole().toLowerCase()) {
                    case "admin":
                        ((Admin) user).showAdminMenu(scanner, user, userService, membershipService, workoutService);
                        break;
                    case "trainer":
                        ((Trainer) user).showTrainerMenu(scanner, workoutService);
                        break;
                    case "member":
                        ((Member) user).showMemberMenu(scanner, membershipService);
                        break;
                    default:
                        System.out.println("Unknown role.");
                        break;
                }
            } else {
                System.out.println("Login Failed! Invalid credentials.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while logging in.");
            e.printStackTrace();
        }
    }
    /**
    * Handles the process of adding a new user to the system.
    * Prompts the admin to input username, password, and role,
    * then calls UserService to create the user in the database.
    *
    * @param scanner Scanner object for capturing user input
    * @param userService Service used to interact with the user data layer
    */
    private static void addNewUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        // Check if username already exists
        if (userService.isUsernameTaken(username)) {
            System.out.println("❌ Username already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        // Check if email already exists
        if (userService.isEmailTaken(email)) {
            System.out.println("❌ Email already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        // Check password strength
        if (password.length() < 8) {
            System.out.println("❌ Password must be at least 8 characters long.");
            return;
        }
        // Check if password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            System.out.println("❌ Password must contain at least one digit.");
            return;
        }
        // Check if password contains at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            System.out.println("❌ Password must contain at least one special character.");
            return;
        }

        System.out.print("Enter role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();
        // Validate role
        if (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Trainer") && !role.equalsIgnoreCase("Member")) {
            System.out.println("❌ Invalid role. Please enter Admin, Trainer, or Member.");
            return;
        }

        try {
            userService.registerUser(username, password, "email", "phone", "address", role);  // Using the registerUser method
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
}
