package org.keyin;

import org.keyin.membership.MembershipService;
import org.keyin.membership.MembershipDAOImpl;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.user.UserDaoImpl;
import org.keyin.user.child_classes.Admin;
import org.keyin.user.child_classes.Member;
import org.keyin.user.child_classes.Trainer;
import org.keyin.workoutclasses.WorkoutClassService;
import org.keyin.workoutclasses.WorkoutClassDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class GymApp {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(new UserDaoImpl());
        MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
        WorkoutClassService workoutService = new WorkoutClassService(new WorkoutClassDAOImpl());

        while (true) {
            System.out.println("\n🌟=== Welcome to the Gym Management System ===🌟");
            System.out.println("1. 🧑‍💻 Register New User");
            System.out.println("2. 🔐 Login");
            System.out.println("3. 🚪 Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNewUser(scanner, userService);
                    break;
                case 2:
                    logInAsUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }

    private static void addNewUser(Scanner scanner, UserService userService) {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            if (userService.isUsernameTaken(username)) {
                System.out.println("Username taken.");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (userService.isEmailTaken(email)) {
                System.out.println("Email already exists.");
                return;
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Phone: ");
            String phone = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            System.out.print("Role (Admin/Trainer/Member): ");
            String role = scanner.nextLine();

            userService.registerUser(username, password, email, phone, address, role);
            System.out.println("✅ Registered successfully.");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void logInAsUser(Scanner scanner, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userService.login(username, password);
            if (user == null) {
                System.out.println("❌ Login failed.");
                return;
            }

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
                    System.out.println("❌ Unknown role.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
    }
}

