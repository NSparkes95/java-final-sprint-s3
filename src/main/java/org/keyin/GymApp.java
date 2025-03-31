package org.keyin;

import org.keyin.membership.Membership;
import org.keyin.membership.MembershipService;
import org.keyin.membership.MembershipDAOImpl;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.workoutclasses.WorkoutClassService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GymApp {
    public static void main(String[] args) throws SQLException {
        // Initialize services with DAO injection
        UserService userService = new UserService();
        MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
        WorkoutClassService workoutService = new WorkoutClassService();

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Gym Management System ===");
            System.out.println("1. Add a new user");
            System.out.println("2. Login as a user");
            System.out.println("3. Exit");
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
                    addNewUser(scanner, userService);
                    break;
                case 2:
                    logInAsUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
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
            User user = userService.loginForUser(username, password);
            if (user != null) {
                System.out.println("Login Successful! Welcome " + user.getUserName());
                switch (user.getUserRole().toLowerCase()) {
                    case "admin":
                        showAdminMenu(scanner, user, userService, membershipService, workoutService);
                        break;
                    case "trainer":
                        showTrainerMenu(scanner, user, membershipService, workoutService);
                        break;
                    case "member":
                        showMemberMenu(scanner, user, membershipService);
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

    // Member menu
    private static void showMemberMenu(Scanner scanner, User user, MembershipService membershipService) {
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
                    try {
                        System.out.print("Enter membership type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter cost: ");
                        double cost = Double.parseDouble(scanner.nextLine());
                        LocalDate start = LocalDate.now();
                        LocalDate end = start.plusMonths(1); // default 1 month

                        Membership membership = new Membership(type, desc, cost, user.getUserId(), start, end);
                        membershipService.buyMembership(membership);
                        System.out.println("✅ Membership purchased successfully.");
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
                        System.out.println("\n📄 Your Memberships:");
                        for (Membership m : memberships) {
                            System.out.println(m);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
                        double total = memberships.stream().mapToDouble(Membership::getMembershipCost).sum();
                        System.out.println("💰 Total spent on memberships: $" + total);
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Trainer menu with membership purchase and view assigned classes
    private static void showTrainerMenu(Scanner scanner, User user, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. Buy Membership");
            System.out.println("2. View Assigned Classes (Coming Soon)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter membership type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter cost: ");
                        double cost = Double.parseDouble(scanner.nextLine());
                        LocalDate start = LocalDate.now();
                        LocalDate end = start.plusMonths(1); // default 1 month

                        Membership membership = new Membership(type, desc, cost, user.getUserId(), start, end);
                        membershipService.buyMembership(membership);
                        System.out.println("✅ Membership purchased successfully.");
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("📋 Feature coming soon: View assigned classes.");
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Admin menu for managing users and viewing stats
    private static void showAdminMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View all users");
            System.out.println("2. View all memberships");
            System.out.println("3. View total membership revenue");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        List<User> users = userService.getAllUsers();
                        System.out.println("\n📋 Registered Users:");
                        for (User u : users) {
                            System.out.println(u);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Failed to load users: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        List<Membership> memberships = membershipService.getAllMemberships();
                        System.out.println("\n📋 All Memberships:");
                        for (Membership m : memberships) {
                            System.out.println(m);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Failed to load memberships: " + e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        double revenue = membershipService.getTotalRevenue();
                        System.out.println("💰 Total revenue from memberships: $" + revenue);
                    } catch (Exception e) {
                        System.out.println("❌ Failed to calculate revenue: " + e.getMessage());
                    }
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Minimal implementation of adding a new user
    private static void addNewUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();

        User user = new User(username, password, role);
        try {
            userService.addUser(user);
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
}
