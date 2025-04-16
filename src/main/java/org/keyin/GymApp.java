// GymApp.java
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

/**
 * Main class for the Gym Management System.
 * This handles login, registration, and role-based menu access
 * for Admins, Trainers, and Members.
 */
public class GymApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService(new UserDaoImpl());
    private static final MembershipService membershipService = new MembershipService(new MembershipDAOImpl());
    private static final WorkoutClassService workoutClassService = new WorkoutClassService(new WorkoutClassDAOImpl());

    /**
     * Entry point of the application. Presents the login/register menu
     * and routes users to their respective dashboards.
     */
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

    /**
     * Handles login for a user and opens their respective dashboard
     * based on their role.
     */
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

    /**
     * Prompts the user for registration details and attempts to create an account.
     */
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

    /**
 * Displays the Admin dashboard menu and handles admin actions.
 *
 * @param loggedInUser the currently logged-in admin user
 */
private static void showAdminMenu(User loggedInUser) {
    boolean running = true;
    while (running) {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. View all users");
        System.out.println("2. View all memberships and total revenue");
        System.out.println("3. Delete user by ID");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");

        String choice = scanner.next();
        scanner.nextLine();

        switch (choice) {
            case "1":
                List<User> users = userService.getAllUsers();
                System.out.printf("%-5s %-25s %-30s %-10s%n", "ID", "Username", "Email", "Role");
                System.out.println("---------------------------------------------------------------------------------");
                for (User user : users) {
                    System.out.printf("%-5d %-25s %-30s %-10s%n",
                            user.getId(), user.getUsername(), user.getEmail(), user.getRole());
                }
                break;
            case "2":
                List<Membership> memberships = membershipService.getAllMemberships();
                System.out.println("\nMemberships:");
                System.out.printf("%-5s %-12s %-35s %-10s %-10s%n", "ID", "Type", "Description", "Cost", "Member ID");
                System.out.println("--------------------------------------------------------------------------------");
                for (Membership m : memberships) {
                    System.out.printf("%-5d %-12s %-35s $%-9.2f %-10d%n",
                            m.getMembershipId(), m.getMembershipType(), m.getMembershipDescription(),
                            m.getMembershipCost(), m.getMemberId());
                }
                double total = membershipService.getTotalRevenue();
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("Total Revenue: $" + total);
                break;
            case "3":
                System.out.print("Enter user ID to delete: ");
                int userIdToDelete = scanner.nextInt();
                boolean deleted = userService.deleteUser(userIdToDelete);
                System.out.println(deleted ? "User deleted successfully." : "Failed to delete user.");
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}

     /**
 * Displays the Trainer dashboard menu and allows trainers to manage classes and memberships.
 *
 * @param loggedInUser the currently logged-in trainer
 */
private static void showTrainerMenu(User loggedInUser) {
    boolean running = true;
    while (running) {
        System.out.println("\n=== Trainer Menu ===");
        System.out.println("1. Add workout class");
        System.out.println("2. Buy membership");
        System.out.println("3. View my workout classes");
        System.out.println("4. Delete workout class");
        System.out.println("5. Update workout class"); 
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
                    System.out.printf("%-5s %-25s %-30s %-12s %-10s %-10s %-12s %-8s %-18s %-10s%n",
                            "ID", "Name", "Description", "Level", "Duration", "Capacity", "Date", "Time", "Location", "Done");
                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (WorkoutClass wc : trainerClasses) {
                        String shortDesc = wc.getClassDescription().length() > 28
                                ? wc.getClassDescription().substring(0, 25) + "..."
                                : wc.getClassDescription();
                        System.out.printf("%-5d %-25s %-30s %-12s %-10d %-10d %-12s %-8s %-18s %-10s%n",
                                wc.getClassId(), wc.getClassName(), shortDesc, wc.getClassLevel(),
                                wc.getClassDuration(), wc.getClassCapacity(), wc.getClassDate(),
                                wc.getClassTime(), wc.getClassLocation(), wc.isCompleted() ? "Yes" : "No");
                    }
                } catch (SQLException e) {
                    System.out.println("Error loading classes: " + e.getMessage());
                }
                break;
            case "4":
                handleDeleteWorkoutClass();
                break;
            case "5":
                handleUpdateWorkoutClass(loggedInUser);
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}

    /**
     * Displays the Member dashboard with options to browse classes,
     * purchase a membership, or view current memberships.
     *
     * @param loggedInUser the currently logged-in member
     */
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
                    System.out.printf("%-5s %-25s %-35s %-12s %-10s %-10s %-12s %-8s %-18s %-10s%n",
                            "ID", "Name", "Description", "Level", "Duration", "Capacity", "Date", "Time", "Location", "Done");
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (WorkoutClass wc : availableClasses) {
                        String shortDesc = wc.getClassDescription().length() > 33
                                ? wc.getClassDescription().substring(0, 30) + "..."
                                : wc.getClassDescription();
                        System.out.printf("%-5d %-25s %-35s %-12s %-10d %-10d %-12s %-8s %-18s %-10s%n",
                                wc.getClassId(), wc.getClassName(), shortDesc, wc.getClassLevel(),
                                wc.getClassDuration(), wc.getClassCapacity(), wc.getClassDate(),
                                wc.getClassTime(), wc.getClassLocation(), wc.isCompleted() ? "Yes" : "No");
                    }
                } catch (SQLException e) {
                    System.out.println("Error loading classes: " + e.getMessage());
                }
                break;
            case "2":
                handleBuyMembership(loggedInUser);
                break;
            case "3":
                List<Membership> myMemberships = membershipService.getMembershipsByMemberId(loggedInUser.getId());
                System.out.println("\nMy Memberships:");
                System.out.println("--------------------------------------------------");
                for (Membership membership : myMemberships) {
                    System.out.println("Membership ID : " + membership.getMembershipId());
                    System.out.println("Type          : " + membership.getMembershipType());
                    System.out.println("Description   : " + membership.getMembershipDescription());
                    System.out.println("Cost          : $" + membership.getMembershipCost());
                    System.out.println("Start Date    : " + (membership.getStartDate() != null ? membership.getStartDate() : "N/A"));
                    System.out.println("End Date      : " + (membership.getEndDate() != null ? membership.getEndDate() : "N/A"));
                    System.out.println("On Hold       : " + (membership.isOnHold() ? "Yes" : "No"));
                    System.out.println("--------------------------------------------------");
                }
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Handles the logic for purchasing a membership for a user.
     *
     * @param loggedInUser the user buying the membership
     */
    private static void handleBuyMembership(User loggedInUser) {
        System.out.print("Enter membership type (e.g., Basic, Premium): ");
        String type = scanner.nextLine();
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter cost: ");
        String costStr = scanner.nextLine();

        double cost = Double.parseDouble(costStr);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        Membership newMembership = new Membership(
                type, desc, cost, loggedInUser.getId(), startDate, endDate, false
        );

        membershipService.buyMembership(newMembership);
        System.out.println("Membership purchased successfully!");
    }

    /**
     * Collects information and creates a new workout class for a trainer.
     *
     * @param loggedInUser the trainer creating the class
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
        String equipment = scanner.nextLine();

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

    /**
     * Prompts trainer to enter an ID and deletes the corresponding class.
     */
    private static void handleDeleteWorkoutClass() {
        System.out.print("Enter ID of the class to delete: ");
        String classIdStr = scanner.nextLine();
        if (classIdStr == null) return;

        int classId = Integer.parseInt(classIdStr);
        try {
            workoutClassService.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.out.println("Failed to delete class: " + e.getMessage());
        }
    }

    /**
     * Prompts trainer to enter class details and updates the corresponding class.
     *
     * @param trainer the trainer updating the class
     */
    private static void handleUpdateWorkoutClass(User trainer) {
        System.out.print("Enter ID of the class to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
    
        WorkoutClass existing = null;
        try {
            List<WorkoutClass> classes = workoutClassService.getWorkoutClassesByTrainerId(trainer.getId());
            for (WorkoutClass wc : classes) {
                if (wc.getClassId() == id) {
                    existing = wc;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve class: " + e.getMessage());
            return;
        }
    
        if (existing == null) {
            System.out.println("No class found with that ID.");
            return;
        }
    
        boolean updating = true;
        while (updating) {
            System.out.println("\nCurrent class details:");
            System.out.println("1. Name: " + existing.getClassName());
            System.out.println("2. Description: " + existing.getClassDescription());
            System.out.println("3. Level: " + existing.getClassLevel());
            System.out.println("4. Duration: " + existing.getClassDuration());
            System.out.println("5. Capacity: " + existing.getClassCapacity());
            System.out.println("6. Date: " + existing.getClassDate());
            System.out.println("7. Time: " + existing.getClassTime());
            System.out.println("8. Location: " + existing.getClassLocation());
            System.out.println("9. Equipment: " + existing.getClassEquipment());
            System.out.println("10. Completed: " + (existing.isCompleted() ? "Yes" : "No"));
            System.out.println("0. Save and Exit");
    
            System.out.print("Select a field to update: ");
            String option = scanner.nextLine();
    
            switch (option) {
                case "1":
                    System.out.print("New name: ");
                    existing.setClassName(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("New description: ");
                    existing.setClassDescription(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("New level: ");
                    existing.setClassLevel(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("New duration (minutes): ");
                    existing.setClassDuration(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case "5":
                    System.out.print("New capacity: ");
                    existing.setClassCapacity(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case "6":
                    System.out.print("New date (YYYY-MM-DD): ");
                    existing.setClassDate(LocalDate.parse(scanner.nextLine()));
                    break;
                case "7":
                    System.out.print("New time (HH:MM): ");
                    existing.setClassTime(LocalTime.parse(scanner.nextLine()));
                    break;
                case "8":
                    System.out.print("New location: ");
                    existing.setClassLocation(scanner.nextLine());
                    break;
                case "9":
                    System.out.print("New equipment: ");
                    existing.setClassEquipment(scanner.nextLine());
                    break;
                case "10":
                    System.out.print("Is the class completed? (true/false): ");
                    existing.setCompleted(scanner.nextBoolean());
                    scanner.nextLine();
                    break;
                case "0":
                    updating = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    
        boolean success = workoutClassService.updateWorkoutClass(existing);
        System.out.println(success ? "\nClass updated successfully!" : "\nFailed to update class.");
    }
    
    
    
}
