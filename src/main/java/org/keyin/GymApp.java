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
                    logInAsUser(scanner,userService, membershipService, workoutService); // routes to role-specific menu
                    break;
                case 2:
                    addNewUser(scanner, userService);
                    break;
                case 0:
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
                case"1":
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
                        System.out.println("💰 Total Revenue from Memberships: $" + total);
                    }   catch (SQLException e) {
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
    * Displays the Admin > User Management submenu, allowing the admin
    * to add, view, update, or delete users.
    *
    * @param scanner Scanner object for user input
    * @param userService Service used to handle user-related database actions
    */

    private static void showAdminUserMenu (Scanner scanner, UserService userService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== 👤Admin User Management Menu ===");
            System.out.println("1. Add New User");
            System.out.println("2. View All Users");
            System.out.println("3. Delete User");
            System.out.println("4. Update User");
            System.out.println("5. View User Details");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": 
                    addNewUser(scanner, userService);
                    break;
                case "2":
                    try {
                        List<User> users = userService.getAllUsers();
                        System.out.println("\n📋 All Users:");
                        for (User u : users) {
                            System.out.println(u);
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving users: " + e.getMessage());
                    }
                    break;
                case "3":
                    deleteUser(scanner, userService);
                    break;
                case "4":
                    updateUser(scanner, userService);
                    break;
                case "5":
                    System.out.print("Enter user ID to view details: ");
                    int userIdToView = Integer.parseInt(scanner.nextLine());
                    try {
                        User user = userService.getUserById(userIdToView);
                        if (user != null) {
                            System.out.println("\n📋 User Details:");
                            System.out.println(user);
                        } else {
                            System.out.println("❌ User not found.");
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving user details: " + e.getMessage());
                    }
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
<<<<<<< HEAD

    /**
    * Updates an existing user by prompting the admin for user ID and new values.
    * The admin can update the username, email, password, and role.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service for handling user-related database operations
    */
    private static void updateUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID to update: ");
        int userId = Integer.parseInt(scanner.nextLine());

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found.");
                return;
            }

            System.out.print("Enter new username (or press Enter to keep current): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty() && !userService.isUsernameTaken(newUsername)) {
                user.setUserName(newUsername);
            }

            System.out.print("Enter new email (or press Enter to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty() && !userService.isEmailTaken(newEmail)) {
                user.setEmail(newEmail);
            }

            System.out.print("Enter new password (or press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            System.out.print("Enter new role (or press Enter to keep current): ");
            String newRole = scanner.nextLine();
            if (!newRole.isEmpty()) {
                user.setUserRole(newRole);
            }

            userService.updateUser(user);
            System.out.println("✅ User updated successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
        }
    }

    /**
    * Prompts the admin to delete a user by entering their ID.
    * Confirms existence before deletion to prevent accidental removal.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service used to access and modify user data
    */
    private static void deleteUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID to delete: ");
        int userId = Integer.parseInt(scanner.nextLine());

        try{
            User user = userService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this user? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                userService.deleteUser(userId);
                System.out.println("✅ User deleted successfully.");
            } else {
                System.out.println("❌ User deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
    
    /**
    * Displays the Admin > Membership Management submenu,
    * allowing the admin to view all memberships and check total revenue.
    *
    * @param scanner Scanner for admin input
    * @param membershipService Service for accessing membership records and revenue
    */
    private static void showAdminMembershipMenu(Scanner scanner, MembershipService membershipService) {
        boolean managingMemberships = true;

        while (running) {
            System.out.println("\n=== 💳Admin Membership Management Menu ===");
            System.out.println("1. View All Memberships");
            System.out.println("2. Check Total Revenue");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllMemberships(scanner, membershipService);
                    break;
                case "2":
                    try {
                        double totalRevenue = membershipService.getTotalRevenue();
                        System.out.println("💰 Total Revenue from Memberships: $" + totalRevenue);
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving total revenue: " + e.getMessage());
                    }
                    break;
                case "0":
                    managingMemberships = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Retrieves and displays all memberships stored in the system.
    *
    * @param membershipService Service used to fetch membership data
    */
    private static void viewAllMemberships(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getAllMemberships();
            if (memberships.isEmpty()) {
                System.out.println("No memberships found.");
            } else {
                System.out.println("\n📋 All Memberships:");
                for (Membership m : memberships) {
                    System.out.println(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving memberships: " + e.getMessage());
        }
    }

    /**
    * Prompts the user to enter workout class details and adds the new class.
    *
    * @param scanner Scanner for user input
    * @param service WorkoutClassService for DAO access
    */
    private static void addNewWorkoutClass(Scanner scanner, WorkoutClassService service) {
        try {
            System.out.println("\n=== Add New Workout Class ===");

            System.out.print("Enter class name: ");
            String name = scanner.nextLine();

            System.out.print("Enter class description: ");
            String description = scanner.nextLine();

            System.out.print("Trainer ID: ");
            int trainerId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class level (Beginner/Intermediate/Advanced): ");
            String level = scanner.nextLine();

            System.out.print("Enter class duration (in minutes): ");
            int duration = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter class date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter class time (HH:MM): ");
            LocalTime time = LocalTime.parse(scanner.nextLine());

            System.out.print("Enter class location: ");
            String location = scanner.nextLine();

            System.out.print("Enter equipment needed (or 'None'): ");
            String equipment = scanner.nextLine();

            // By default, newly created classes are not full
            boolean isFull = false;

            // Create a new WorkoutClass object
            WorkoutClass workoutClass = new WorkoutClass(
                0, name, trainerId, level, description, 
                duration, capacity, date, time, location, equipment
            );

            service.addWorkoutClass(workoutClass);
            System.out.println("Workout class added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding workout class: " + e.getMessage());
        }
    }

=======
>>>>>>> c79cc4cb7705da93444e93e051fb3cdf4ca4f216
}
