/**
 * Main application entry point for the Gym Management System.
 * Supports Admin, Trainer, and Member role-based menus.
 */
package org.keyin;

import org.keyin.membership.*;
import org.keyin.user.*;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.workoutclasses.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;


/**
 * The main application class for the Gym Management System.
 * Provides a console-based interface for users to register, login,
 * and perform actions based on their roles (Admin, Trainer, Member).
 */
public class GymApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService(new UserDaoImpl());
    private static final MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
    private static final WorkoutClassService workoutClassService = new WorkoutClassService(new WorkoutClassDAOImpl());

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Welcome to the Gym Management System ===");
            System.out.print("Do you want to (1) Login or (2) Register or (0) Exit? ");
            String initialChoice = scanner.nextLine();

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

    /**
     * Handles user login and redirects to the appropriate menu based on role.
     */
    private static void logInAsUser() {
        System.out.print("Enter your email to login: ");
        String email = scanner.nextLine();

        User loggedInUser = userService.login(email);

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

    /**
     * Handles user registration by collecting necessary details and calling the service layer.
     */
    private static void handleUserRegistration() {
        System.out.println("=== User Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (Admin / Member / Trainer): ");
        String role = scanner.nextLine();

        boolean registered = userService.registerUser(username, email, password, role);

        if (registered) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Email might already be in use.");
        }
    }

    /**
     * Displays admin-specific options such as viewing users and revenue.
     */
    private static void showAdminMenu(User loggedInUser) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View all users");
            System.out.println("2. View all memberships and total revenue");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

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
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } 
    }

    /**
     * Displays trainer-specific menu options.
     */
    private static void showTrainerMenu(User loggedInUser) {
        System.out.println("\n=== Trainer Menu ===");
        System.out.println("1. Add workout class");
        System.out.println("2. Buy membership");
        System.out.println("3. View my workout classes");
        System.out.println("4. Delete workout class");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
        String choice;

        try {
            if(!scanner.hasNextLine()) {
                System.out.println("No input detected. Exiting...");
                return;
            }
            choice = scanner.nextLine();
        }  catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("No input received. ");
            return;
        }
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
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Displays member-specific options.
     */
    private static void showMemberMenu(User loggedInUser) {
        System.out.println("\n=== Member Menu ===");
        System.out.println("1. Browse workout classes");
        System.out.println("2. Buy membership");
        System.out.println("3. View my memberships");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

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
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Logic for purchasing a new membership.
     */
    private static void handleBuyMembership(User loggedInUser) {
        System.out.print("Enter membership type (e.g., Basic, Premium): ");
        String type = scanner.nextLine();

        System.out.print("Enter description: ");
        String desc = scanner.nextLine();

        System.out.print("Enter cost: ");
        double cost = Double.parseDouble(scanner.nextLine());

        Membership newMembership = new Membership(type, desc, cost, loggedInUser.getId());
        membershipService.buyMembership(newMembership);

        System.out.println("Membership purchased successfully!");
    }

    /**
     * Trainer workflow for adding a new workout class.
     */
    private static void handleAddWorkoutClass(User loggedInUser) {
        System.out.print("Enter workout class name: ");
        String name = scanner.nextLine();

        System.out.print("Enter class description: ");
        String description = scanner.nextLine();

        System.out.print("Enter level: ");
        String level = scanner.nextLine();

        System.out.print("Enter duration (in minutes): ");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter time (HH:MM): ");
        String time = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter equipment: ");
        String equipment = scanner.nextLine();

        WorkoutClass newClass = new WorkoutClass(
                0, name, loggedInUser.getId(), description, level, duration, capacity,
                java.time.LocalDate.parse(date), java.time.LocalTime.parse(time), location, equipment);

        try {
            workoutClassService.addWorkoutClass(newClass);
            System.out.println("Workout class added successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to add workout class: " + e.getMessage());
        }
    }

    /**
     * Deletes a workout class by its ID.
     */
    private static void handleDeleteWorkoutClass() {
        System.out.print("Enter ID of the class to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());
        try {
            workoutClassService.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.out.println("Failed to delete class: " + e.getMessage());
        }
    }
}
