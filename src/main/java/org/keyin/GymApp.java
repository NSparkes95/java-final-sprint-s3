package org.keyin;

import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.user.UserDaoImpl;
import org.keyin.user.childclasses.Admin;
import org.keyin.user.childclasses.Member;
import org.keyin.user.childclasses.Trainer;
import org.keyin.membership.MembershipService;
import org.keyin.membership.MembershipDAOImpl;
import org.keyin.workoutclasses.WorkoutClass;
import org.keyin.workoutclasses.WorkoutClassService;
import org.keyin.workoutclasses.WorkoutClassDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main class for the Gym Management System.
 * This class handles user registration, login, and menu navigation for different user roles.
 * It uses services to manage users, memberships, and workout classes.
 */
public class GymApp {
    /**
     * Main method to run the Gym Management System.
     * It initializes services and handles user input for registration and login.
     *
     * @param args Command line arguments (not used)
     * @throws SQLException If a database access error occurs
     */
    // Main method to run the Gym Management System
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

    /**
     * Prompts the user for registration details and registers a new user.
     *
     * @param scanner    Scanner object for user input
     * @param userService The service to manage users
     */
    // Registration method for new users
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

    /**
     * Prompts the user to log in and redirects them to the appropriate menu based on their role.
     *
     * @param scanner        Scanner object for user input
     * @param userService    The service to manage users
     * @param membershipService The service to manage memberships
     * @param workoutService The service to manage workout classes
     */
    // Login method for users to access their respective menus
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

    /**
     * Displays the admin menu for managing users, memberships, and workout classes.
     *
     * @param scanner        Scanner object for user input
     * @param user           The logged-in admin
     * @param userService    The service to manage users
     * @param membershipService The service to manage memberships
     * @param workoutService The service to manage workout classes
     */
    // Admin menu for managing users, memberships, and workout classes
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

    /**
     * Displays the member menu for managing memberships and expenses.
     *
     * @param scanner        Scanner object for user input
     * @param user           The logged-in member
     * @param membershipService The service to manage memberships
     */
    // Member menu for managing memberships and expenses
    public static void showMemberMenu(Scanner scanner, User user, MembershipService membershipService) {
        Member member = (Member) user;
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
                    member.buyMembership(scanner, membershipService);
                    break;
                case "2":
                    member.viewMyMemberships(membershipService);
                    break;
                case "3":
                    member.checkMyExpenses(membershipService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays the trainer menu for managing workout classes.
     *
     * @param scanner        Scanner object for user input
     * @param user           The logged-in trainer
     * @param workoutService The service to manage workout classes
     */
    // Trainer menu for managing workout classes
    public static void showTrainerMenu(Scanner scanner, User user, WorkoutClassService workoutService) {
        Trainer trainer = (Trainer) user;
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
                    trainer.viewMyClasses(workoutService);
                    break;
                case "2":
                    trainer.addClass(scanner, workoutService);
                    break;
                case "3":
                    trainer.updateClass(scanner, workoutService);
                    break;
                case "4":
                    trainer.deleteClass(scanner, workoutService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    // Admin user management, membership management, workout class management, and trainer management

    // Admin user management
    private static void showAdminUserMenu(Scanner scanner, UserService userService) {
        boolean running = true;
    
        while (running) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    // View all users
                    userService.getAllUsers().forEach(System.out::println);  // userService.getAllUsers() returns a list of all users
                    break;
                case "2":
                    // Delete a user
                    System.out.print("Enter the user ID to delete: ");
                    int userIdToDelete = Integer.parseInt(scanner.nextLine());
                    userService.deleteUserById(userIdToDelete);(userIdToDelete);  // userService.deleteUser(userId) deletes the user
                    System.out.println("User deleted.");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
    
    // Admin membership management
    private static void showAdminMembershipMenu(Scanner scanner, MembershipService membershipService) {
        boolean running = true;
    
        while (running) {
            System.out.println("\n=== Membership Management ===");
            System.out.println("1. View All Memberships");
            System.out.println("2. Cancel Membership");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    // View all memberships
                    membershipService.getAllMemberships().forEach(System.out::println);  // fetches all memberships
                    break;
                case "2":
                    // Cancel a membership
                    System.out.print("Enter the membership ID to cancel: ");
                    int membershipIdToCancel = Integer.parseInt(scanner.nextLine());
                    membershipService.cancelMembership(membershipIdToCancel);  // cancelMembership cancels the membership
                    System.out.println("Membership canceled.");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
    
    // Admin workout class management
    private static void showAdminWorkoutClassMenu(Scanner scanner, WorkoutClassService workoutService) {
        boolean running = true;
    
        while (running) {
            System.out.println("\n=== Workout Class Management ===");
            System.out.println("1. View All Classes");
            System.out.println("2. Add New Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    // View all workout classes
                    workoutService.getAllWorkoutClasses().forEach(System.out::println);  // fetches all workout classes
                    break;
                case "2":
                    // Add new workout class
                    System.out.print("Enter class name: ");
                    String className = scanner.nextLine();
                    System.out.print("Enter class description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter trainer ID: ");
                    int trainerId = Integer.parseInt(scanner.nextLine());
                    // Other input for class can be added here
                    WorkoutClass newClass = new WorkoutClass(className, trainerId, description);
                    workoutService.addWorkoutClass(newClass);  // addWorkoutClass adds a new class
                    System.out.println("Class added.");
                    break;
                case "3":
                    // Update an existing class
                    System.out.print("Enter class ID to update: ");
                    int classIdToUpdate = Integer.parseInt(scanner.nextLine());
                    WorkoutClass existingClass = workoutService.getClassById(classIdToUpdate);  // getClassById fetches the class
                    if (existingClass != null) {
                        System.out.print("Enter new class name (leave empty to keep current): ");
                        String newClassName = scanner.nextLine();
                        if (!newClassName.isEmpty()) {
                            existingClass.setClassName(newClassName);
                        }
                        // Repeat for other class attributes...
                        workoutService.updateWorkoutClass(existingClass);
                        System.out.println("Class updated.");
                    } else {
                        System.out.println("❌ Class not found.");
                    }
                    break;
                case "4":
                    // Delete a workout class
                    System.out.print("Enter class ID to delete: ");
                    int classIdToDelete = Integer.parseInt(scanner.nextLine());
                    workoutService.deleteWorkoutClass(classIdToDelete);  // deleteWorkoutClass deletes the class
                    System.out.println("Class deleted.");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
    
    // Admin trainer management
    private static void showAdminTrainerMenu(Scanner scanner, UserService userService) {
        boolean running = true;
    
        while (running) {
            System.out.println("\n=== Trainer Management ===");
            System.out.println("1. View All Trainers");
            System.out.println("2. Assign Trainer to Class");
            System.out.println("3. Unassign Trainer from Class");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
    
            switch (choice) {
                case "1":
                    // View all trainers
                    userService.getAllTrainers().forEach(System.out::println);  // getAllTrainers fetches all trainers
                    break;
                case "2":
                    // Assign trainer to class
                    System.out.print("Enter trainer ID: ");
                    int trainerIdToAssign = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter class ID to assign trainer to: ");
                    int classIdToAssign = Integer.parseInt(scanner.nextLine());
                    userService.assignTrainerToClass(trainerIdToAssign, classIdToAssign);  // this method assigns a trainer to a class
                    System.out.println("Trainer assigned.");
                    break;
                case "3":
                    // Unassign trainer from class
                    System.out.print("Enter trainer ID to unassign: ");
                    int trainerIdToUnassign = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter class ID to unassign trainer from: ");
                    int classIdToUnassign = Integer.parseInt(scanner.nextLine());
                    userService.unassignTrainerFromClass(trainerIdToUnassign, classIdToUnassign);  // this method unassigns a trainer from a class
                    System.out.println("Trainer unassigned.");
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