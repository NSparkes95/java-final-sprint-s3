package org.keyin;

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
            User user = userService.login(username, password);
            if (user != null) {
                System.out.println("Login Successful! Welcome " + user.getUserName());
                switch (user.getRole().toLowerCase()) {
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

    // Trainer menu with membership and workout class features
    private static void showTrainerMenu(Scanner scanner, User user, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. Buy Membership");
            System.out.println("2. View My Upcoming Classes");
            System.out.println("3. Add New Class");
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
                        LocalDate end = start.plusMonths(1);

                        Membership membership = new Membership(type, desc, cost, user.getUserId(), start, end);
                        membershipService.buyMembership(membership);
                        System.out.println("✅ Membership purchased successfully.");
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        List<WorkoutClass> upcomingClasses = workoutService.getUpcomingClasses(user.getUserId());
                        System.out.println("\n📋 Your Upcoming Classes:");
                        for (WorkoutClass wc : upcomingClasses) {
                            System.out.println(wc);
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Error retrieving classes: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        System.out.print("Enter class name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter class description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter time (HH:MM): ");
                        LocalTime time = LocalTime.parse(scanner.nextLine());
                        System.out.print("Enter duration (in minutes): ");
                        int duration = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter capacity: ");
                        int capacity = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter level (Beginner/Intermediate/Advanced): ");
                        String level = scanner.nextLine();
                        System.out.print("Enter equipment needed (or 'None'): ");
                        String equipment = scanner.nextLine();

                        WorkoutClass wc = new WorkoutClass(0, name, user.getUserId(), level, desc, duration, capacity, date, time, location, equipment);
                        workoutService.addWorkoutClass(wc);
                        System.out.println("✅ Class added successfully!");
                    } catch (Exception e) {
                        System.out.println("❌ Error adding class: " + e.getMessage());
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

    // Admin menu
    private static void showAdminMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. User Management");
            System.out.println("2. Membership Management");
            System.out.println("3. Workout Class Management");
            System.out.println("4. Trainer Management");
            System.out.println("5. View Total Revenue");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAdminMenu(scanner, userService);
                    break;
                case "2":
                    try {
                        List<Membership> memberships = membershipService.getAllMemberships();
                        System.out.println("\n📄 All Memberships:");
                        for (Membership m : memberships) {
                            System.out.println(m);
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error fetching memberships: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        double total = membershipService.getTotalRevenue();
                        System.out.println("💰 Total Revenue from Memberships: $" + total);
                    } catch (SQLException e) {
                        System.out.println("❌ Error calculating revenue: " + e.getMessage());
                    }
                    break;
                case "4":
                    addNewWorkoutClass(scanner, workoutService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addNewUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin/Trainer/Member): ");
        String role = scanner.nextLine();

        try {
            userService.registerUser(username, password, "email", "phone", "address", role);  // Using the registerUser method
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
}
