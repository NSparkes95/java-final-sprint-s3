// GymApp.java (Fixed Input Handling)
package org.keyin;

import org.keyin.membership.*;
import org.keyin.user.*;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.workoutclasses.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


public class GymApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService(new UserDaoImpl());
    private static final MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
    private static final WorkoutClassService workoutClassService = new WorkoutClassService(new WorkoutClassDAOImpl());

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Welcome to the Gym Management System ===");
            System.out.print("Do you want to (1) Login or (2) Register or (0) Exit? ");
            String initialChoice = scanner.next();
            if (initialChoice == null) {
                System.out.println("Input stream closed. Exiting...");
                return;
            }    

            switch (initialChoice) {
                case "1":
                    logInAsUser();
                    break;
                case "2":
                    handleUserRegistration();
                    break;
                case "0":
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void logInAsUser() {
        System.out.println("=== User Login ===");
        scanner.nextLine();
        System.out.print("Enter your email to login: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = userService.login(email, password);

        if (loggedInUser == null) {
            System.out.println("User not found. Please check your email and try again.");
            return;
        }

        System.out.println("Welcome, " + loggedInUser.getUsername() + " (" + loggedInUser.getRole() + ")");

        if (loggedInUser instanceof Admin) {
            showAdminMenu(loggedInUser);
        } else if (loggedInUser instanceof Trainer) {
            showTrainerMenu(loggedInUser);
        } else if (loggedInUser instanceof Member) {
            showMemberMenu(loggedInUser);
        } else {
            System.out.println("Unrecognized role. Contact system admin.");
        }
    }

    private static void handleUserRegistration() {
        System.out.println("=== User Registration ===");
        scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin / Member / Trainer): ");
        String role = scanner.nextLine();

        if (username == null || email == null || password == null || role == null) return;

        boolean registered = userService.registerUser(username, email, password, role);

        if (registered) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Email might already be in use.");
        }
    }

    private static void showAdminMenu(User loggedInUser) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View all users");
            System.out.println("2. View all memberships and total revenue");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.next();
            scanner.nextLine();

            switch (choice) {
                case "1":
                    List<User> users = userService.getAllUsers();
                    users.forEach(System.out::println);
                    break;
                case "2":
                    List<Membership> memberships = membershipService.getAllMemberships();
                    memberships.forEach(System.out::println);
                    double total = membershipService.getTotalRevenue();
                    System.out.println("Total Revenue: $" + total);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void showTrainerMenu(User loggedInUser) {
        System.out.println("\n=== Trainer Menu ===");
        System.out.println("1. Add workout class");
        System.out.println("2. Buy membership");
        System.out.println("3. View my workout classes");
        System.out.println("4. Delete workout class");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");

        String choice = scanner.next();
        scanner.nextLine();

        switch (choice) {
            case "1":
                handleAddWorkoutClass(loggedInUser);
                break;
            case "2":
                handleBuyMembership(loggedInUser);
                break;
            case "3":
                try {
                    List<WorkoutClass> trainerClasses = workoutClassService.getWorkoutClassesByTrainerId(loggedInUser.getId());
                    trainerClasses.forEach(System.out::println);
                } catch (SQLException e) {
                    System.out.println("Error loading classes: " + e.getMessage());
                }
                break;
            case "4":
                handleDeleteWorkoutClass();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void showMemberMenu(User loggedInUser) {
        System.out.println("\n=== Member Menu ===");
        System.out.println("1. Browse workout classes");
        System.out.println("2. Buy membership");
        System.out.println("3. View my memberships");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");

        String choice = scanner.next();
        scanner.nextLine();

        switch (choice) {
            case "1":
                try {
                    List<WorkoutClass> availableClasses = workoutClassService.getAllWorkoutClasses();
                    availableClasses.forEach(System.out::println);
                } catch (SQLException e) {
                    System.out.println("Error loading classes: " + e.getMessage());
                }
                break;
            case "2":
                handleBuyMembership(loggedInUser);
                break;
            case "3":
                List<Membership> myMemberships = membershipService.getMembershipsByMemberId(loggedInUser.getId());
                myMemberships.forEach(System.out::println);
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handleBuyMembership(User loggedInUser) {
        System.out.print("Enter membership type (e.g., Basic, Premium): ");
        String type = scanner.nextLine();
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter cost: ");
        String costStr = scanner.nextLine();

        double cost = Double.parseDouble(costStr);
        Membership newMembership = new Membership(type, desc, cost, loggedInUser.getId());
        membershipService.buyMembership(newMembership);
        System.out.println("Membership purchased successfully!");
    }

    /**
     * @param loggedInUser
     */
    private static void handleAddWorkoutClass(User loggedInUser) {
        System.out.print("Enter class name: ");
        String name = scanner.nextLine();
        System.out.print("Enter class description: ");
        String description = scanner.nextLine();
        System.out.print("Enter level: ");
        String level = scanner.nextLine();
        System.out.print("Enter duration (minutes): ");
        String durationStr = scanner.nextLine();
        System.out.print("Enter capacity: ");
        String capacityStr = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter time (HH:MM): ");
        String timeStr = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter equipment: ");
        String equipment = scanner.nextLine();;

        try {
            int duration = Integer.parseInt(durationStr);
            int capacity = Integer.parseInt(capacityStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);

            WorkoutClass newClass = new WorkoutClass(
                0, name, loggedInUser.getId(), description, level, duration,
                capacity, date, time, location, equipment
            );

            workoutClassService.addWorkoutClass(newClass);
            System.out.println("Workout class added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add workout class: " + e.getMessage());
        }
    }

    private static void handleDeleteWorkoutClass() {
        System.out.print("Enter ID of the class to delete: ");
        String classIdStr = scanner.nextLine();;
        if (classIdStr == null) return;

        int classId = Integer.parseInt(classIdStr);
        try {
            workoutClassService.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.out.println("Failed to delete class: " + e.getMessage());
        }
    }
}
