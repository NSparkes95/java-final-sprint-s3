package org.keyin;

import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.user.UserDaoImpl;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.membership.MembershipService;
import org.keyin.workoutclasses.WorkoutClassService;
import org.keyin.workoutclasses.WorkoutClassDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main application entry point for the Gym Management System.
 * Manages user login, registration, and routing to role-specific menus.
 */
public class GymApp {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Initialize services
        UserService userService = new UserService(new UserDaoImpl());
        MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
        WorkoutClassService workoutService = new WorkoutClassService(new WorkoutClassDAOImpl());

        // Main application loop
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

    // Method for registering new user
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
            if (password.length() < 8 || !password.matches(".*\\d.*") ||
                !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                System.out.println("❌ Password must be at least 8 characters, include a digit and a special character.");
                return;
            }

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

    // Method for logging in a user
    private static void logInAsUser(Scanner scanner, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userService.login(username, password); // Now the login method takes care of the role assignment
            if (user == null) {
                System.out.println("❌ Login failed.");
                return;
            }

            // Now, based on the role, we directly call the role-specific menu method
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    showAdminMenu(scanner, user, userService, membershipService, workoutService);
                    break;
                case "trainer":
                    showTrainerMenu(scanner, user, workoutService);
                    break;
                case "member":
                    showMemberMenu(scanner, user, membershipService);
                    break;
                default:
                    System.out.println("❌ Unknown role.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
    }

    // Admin menu
    public static void showAdminMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== 🛠️Admin Menu ===");
            System.out.println("1. 👤User Management");
            System.out.println("2. 💳Membership Management");
            System.out.println("3. 🏋️Workout Class Management");
            System.out.println("4. 🧑‍🏫Trainer Management");
            System.out.println("5. 💰View Total Revenue");
            System.out.println("0.🔙Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAdminUserMenu(scanner, userService);
                    break;
                case "2":
                    showAdminMembershipMenu(scanner, membershipService);
                    break;
                case "3":
                    showAdminWorkoutClassMenu(scanner, workoutService);
                    break;
                case "4":
                    showAdminTrainerMenu(scanner, userService);
                    break;
                case "5":
                    try {
                        double totalRevenue = membershipService.getTotalRevenue();
                        System.out.println("💰 Total Revenue from Memberships: $" + totalRevenue);
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving total revenue: " + e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌Invalid option. Try again.");
            }
        }
    }

    // Member menu
    public static void showMemberMenu(Scanner scanner, User user, MembershipService membershipService) {
    Member member = (Member) user;  // Cast to Member type
    boolean running = true;

    while (running) {
        System.out.println("\n=== Member Menu ===");
        System.out.println("1. Buy Membership");
        System.out.println("2. View My Memberships");
        System.out.println("3. Check My Expenses");
        System.out.println("0. Back to Main Menu");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                member.buyMembership(scanner, membershipService);  // Use method from Member class
                break;
            case "2":
                member.viewMyMemberships(membershipService);  // Use method from Member class
                break;
            case "3":
                member.checkMyExpenses(membershipService);  // Use method from Member class
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("❌ Invalid option. Try again.");
        }
    }
}

    // Trainer menu
public static void showTrainerMenu(Scanner scanner, User user, WorkoutClassService workoutService) {
    Trainer trainer = (Trainer) user;  // Cast to Trainer type
    boolean running = true;

    while (running) {
        System.out.println("\n=== Trainer Menu ===");
        System.out.println("1. View My Classes");
        System.out.println("2. Add Class");
        System.out.println("3. Update Class");
        System.out.println("4. Delete Class");
        System.out.println("0. Back to Main Menu");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                trainer.viewMyClasses(workoutService);  // Call viewMyClasses from the Trainer class
                break;
            case "2":
                trainer.addClass(scanner, workoutService);  // Call addClass from the Trainer class
                break;
            case "3":
                trainer.updateClass(scanner, workoutService);  // Call updateClass from the Trainer class
                break;
            case "4":
                trainer.deleteClass(scanner, workoutService);  // Call deleteClass from the Trainer class
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("❌ Invalid option. Try again.");
        }
    }
}

}