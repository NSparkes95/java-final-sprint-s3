package org.keyin;

import org.keyin.membership.MembershipService;
import org.keyin.membership.MembershipDAOImpl;
import org.keyin.user.User;
import org.keyin.user.UserService;
import org.keyin.user.UserDaoImpl;
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
            }

                default:
                    System.out.println("❌ Unknown role.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
    }

    public void showAdminMenu(Scanner scanner, User user, UserService userService,
                               MembershipService membershipService, WorkoutClassService workoutService) {
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

    public void showAdminUserMenu (Scanner scanner, UserService userService) {
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
    * Displays the Admin > Membership Management submenu,
    * allowing the admin to view all memberships and check total revenue.
    *
    * @param scanner Scanner for admin input
    * @param membershipService Service for accessing membership records and revenue
    */
    public void showAdminMembershipMenu(Scanner scanner, MembershipService membershipService) {
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
    * Displays the Admin > Trainer Management submenu.
    * Allows the admin to view, add, update, or delete trainers.
    *
    * @param scanner Scanner for admin input
    * @param userService Service for accessing user data (trainer accounts)
    */
    public void showAdminTrainerMenu(Scanner scanner, UserService userService) {
        boolean managingTrainers = true;

        while (managingTrainers) {
            System.out.println("\n=== 🧑‍🏫Admin Trainer Management Menu ===");
            System.out.println("1. View All Trainers");
            System.out.println("2. Add New Trainer");
            System.out.println("3. Update Trainer Information");
            System.out.println("4. Delete Trainer");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine(); 

            switch (choice) {
                case "1":
                    viewAllTrainers(userService);
                    break;
                case "2":
                    addNewTrainer(scanner, userService);
                    break;
                case "3":
                    updateTrainer(scanner, userService);
                    break;
                case "4":
                    deleteTrainer(scanner, userService);
                    break;
                case "0":
                    managingTrainers = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Updates an existing user by prompting the admin for user ID and new values.
    * The admin can update the username, email, password, and role.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service for handling user-related database operations
    */
    public void updateUser(Scanner scanner, UserService userService) {
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
    }/**
    * Prompts the admin to delete a user by entering their ID.
    * Confirms existence before deletion to prevent accidental removal.
    *
    * @param scanner Scanner for capturing admin input
    * @param userService Service used to access and modify user data
    */
    public void deleteUser(Scanner scanner, UserService userService) {
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
    * Retrieves and displays all memberships stored in the system.
    *
    * @param membershipService Service used to fetch membership data
    */
    public void viewAllMemberships(MembershipService membershipService) {
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
    * Displays the Admin > Workout Class Management submenu,
    * allowing the admin to view and add workout classes.
    *
    * @param scanner Scanner for admin input
    * @param workoutService Service for accessing workout class data
    */
    public void showAdminWorkoutClassMenu(Scanner scanner, WorkoutClassService workoutService) {
        boolean managingClasses = true;

        while (managingClasses) {
            System.out.println("\n=== 🏋️Admin Workout Class Management Menu ===");
            System.out.println("1. View All Classes");
            System.out.println("2. Add New Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllWorkoutClasses(workoutService);
                    break;
                case "2":
                    addNewWorkoutClass(scanner, workoutService);
                    break;
                case "3":
                    updateWorkoutClass(scanner, workoutService);
                    break;
                case "4":
                    deleteWorkoutClass(scanner, workoutService);
                    break;
                case "0":
                    managingClasses = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays a list of all workout classes currently in the system.
    *
    * @param workoutService Service used to fetch workout class data
    */
    public void viewAllWorkoutClasses(WorkoutClassService workoutService) {
        try {
            List<WorkoutClass> classes = workoutService.getAllWorkoutClasses();
            if (classes.isEmpty()) {
                System.out.println("No workout classes found.");
            } else {
                System.out.println("\n📋 All Workout Classes:");
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving workout classes: " + e.getMessage());
        }
    }

    /**
    * Displays all users with the role of 'Trainer'.
    *
    * @param userService Service used to fetch user data
    */
    public void viewAllTrainers(UserService userService) {
        try {
            List<User> trainers = userService.getAllTrainers();
            if (trainers.isEmpty()) {
                System.out.println("No trainers found.");
            } else {
                System.out.println("\n📋 All Trainers:");
                for (User t : trainers) {
                    System.out.println(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving trainers: " + e.getMessage());
        }
    }

    /**
    * Prompts the admin to create a new trainer account.
    * Validates for duplicate username/email and password strength.
    *
    * @param scanner Scanner for input
    * @param userService Service used to create the trainer
    */
    public void addNewTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer username: ");
        String username = scanner.nextLine();
        // Check if username already exists
        if (userService.isUsernameTaken(username)) {
            System.out.println("❌ Username already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter trainer email: ");
        String email = scanner.nextLine();
        // Check if email already exists
        if (userService.isEmailTaken(email)) {
            System.out.println("❌ Email already exists. Please choose a different one.");
            return;
        }
        System.out.print("Enter trainer password: ");
        String password = scanner.nextLine();
        // Check password strength
        if (password.length() < 8 || !password.matches(".*\\d.*") ||
        !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
        System.out.println("❌ Password must be at least 8 characters, include a digit and a special character.");
        return;
        }

        User trainer = new User(username, password, "Trainer");
        trainer.setEmail(email);
        try {
            userService.registerUser(trainer);
            System.out.println("Trainer added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding trainer: " + e.getMessage());
        }
    }

    /**
    * Allows the admin to update trainer information by user ID.
    *
    * @param scanner Scanner for input
    * @param userService Service used to update trainer data
    */
    public void updateTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer ID to update: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null) {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Enter new username (or press Enter to keep '" + trainer.getUserName() + "'): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                trainer.setUserName(newUsername);
            }

            System.out.print("Enter new email (or press Enter to keep '" + trainer.getEmail() + "'): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                trainer.setEmail(newEmail);
            }

            System.out.print("Enter new password (or press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                trainer.setPassword(newPassword);
            }

            userService.updateUser(trainer);
            System.out.println("✅ Trainer updated successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating trainer: " + e.getMessage());
        }
    }
    /**
    * Deletes a trainer account by ID after confirmation.
    *
    * @param scanner Scanner to capture input
    * @param userService Service used to access/delete trainer account
    */
    public void deleteTrainer(Scanner scanner, UserService userService) {
        System.out.print("Enter trainer ID to delete: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            User trainer = userService.getUserById(trainerId);
            if (trainer == null || !trainer.getUserRole().equalsIgnoreCase("Trainer")) {
                System.out.println("❌ Trainer not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this trainer? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                userService.deleteUser(trainerId);
                System.out.println("✅ Trainer deleted successfully.");
            } else {
                System.out.println("❌ Trainer deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting trainer: " + e.getMessage());
        }
    }

        /**
     * Displays the member menu for purchasing and viewing memberships.
     *
     * @param scanner Scanner for user input
     * @param membershipService Service for managing memberships
     */
    public void showMemberMenu(Scanner scanner, User user, MembershipService membershipService) {
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

    private void buyMembership(Scanner scanner, MembershipService membershipService) {
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
            System.out.println("❌ Error purchasing membership: " + e.getMessage());
        }
    }

    private void viewMemberships(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
            System.out.println("\n📄 Your Memberships:");
            for (Membership m : memberships) {
                System.out.println(m);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving memberships: " + e.getMessage());
        }
    }

    private void checkExpenses(MembershipService membershipService) {
        try {
            List<Membership> memberships = membershipService.getMembershipsByUserId(user.getUserId());
            double total = memberships.stream().mapToDouble(Membership::getMembershipCost).sum();
            System.out.println("💰 Total spent on memberships: $" + total);
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving expenses: " + e.getMessage());
        }
    }

    /**
    * Displays the trainer-specific menu, allowing trainers to manage their assigned classes.
    *
    * @param scanner Scanner for user input
    * @param user The currently logged-in trainer
    * @param membershipService Not used by trainers, but included for consistency
    * @param workoutService Service for managing workout classes
    */
    public void showTrainerMenu(Scanner scanner, User user, WorkoutClassService workoutService) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. View My Upcoming Classes");
            System.out.println("2. Add New Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewTrainerClasses(user.getUserId(), workoutService);
                    break;
                case "2":
                    addNewWorkoutClass(scanner, workoutService);
                    break;
                case "3":
                    updateWorkoutClass(scanner, workoutService);
                    break;
                case "4":
                    deleteWorkoutClass(scanner, workoutService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
    * Displays all workout classes created by the logged-in trainer.
    *
    * @param trainerId The ID of the logged-in trainer
    * @param workoutService Service to retrieve the trainer's classes
    */
    public void viewTrainerClasses(int trainerId, WorkoutClassService workoutService) {
        try {
            List<WorkoutClass> classes = workoutService.getClassesByTrainerId(trainerId);
            if (classes.isEmpty()) {
                System.out.println("No classes found for this trainer.");
            } else {
                System.out.println("\n📋 Your Upcoming Classes:");
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving classes: " + e.getMessage());
        }
    }

    /**
    * Prompts the user to enter workout class details and adds the new class.
    *
    * @param scanner Scanner for user input
    * @param service WorkoutClassService for DAO access
    */
    public static void addNewWorkoutClass(Scanner scanner, WorkoutClassService workoutService) {
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

            // Create a new WorkoutClass object
            WorkoutClass workoutClass = new WorkoutClass(
                0, name, trainerId, level, description, 
                duration, capacity, date, time, location, equipment
            );

            workoutService.addWorkoutClass(workoutClass);
            System.out.println("Workout class added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding workout class: " + e.getMessage());
        }
    }

    /**
    * Allows the admin to update details of an existing workout class by ID.
    * Admin can skip any field by pressing Enter to retain the current value.
    *
    * @param scanner Scanner for capturing admin input
    * @param workoutService Service used to access and update class data
    */
    public void updateWorkoutClass(Scanner scanner, WorkoutClassService workoutService){
        try {
            System.out.print("Enter class ID to update: ");
            int classId = Integer.parseInt(scanner.nextLine());

            WorkoutClass existingClass = workoutService.getWorkoutClassById(classId);
            if (existingClass == null) {
                System.out.println("❌ Class not found.");
                return;
            }

            System.out.print("Enter new class name (or press Enter to keep '" + existingClass.getClassName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingClass.setClassName(newName);
            }

            System.out.print("Enter new description (or press Enter to keep '" + existingClass.getDescription() + "'): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingClass.setDescription(newDescription);
            }

            System.out.print("New Trainer ID (or press Enter to keep '" + existingClass.getTrainerId() + "'): ");
            String trainerIdInput = scanner.nextLine();
            if (!trainerIdInput.isEmpty()) {
                int parsedTrainerId = Integer.parseInt(trainerIdInput);
                existingClass.setTrainerId(parsedTrainerId);
            }

            System.out.print("New class level (or press Enter to keep '" + existingClass.getLevel() + "'): ");
            String newLevel = scanner.nextLine();
            if (!newLevel.isEmpty()) {
                existingClass.setLevel(newLevel);
            }

            System.out.print("New duration (or press Enter to keep '" + existingClass.getDuration() + "'): ");
            String newDuration = scanner.nextLine();
            if (!newDuration.isEmpty()) {
                int parsedDuration = Integer.parseInt(newDuration);
                existingClass.setDuration(parsedDuration);
            }

            System.out.print("New capacity (or press Enter to keep '" + existingClass.getCapacity() + "'): ");
            String newCapacity = scanner.nextLine();
            if (!newCapacity.isEmpty()) {
                int parsedCapacity = Integer.parseInt(newCapacity);
                existingClass.setCapacity(parsedCapacity);
                }

            System.out.print("New date (YYYY-MM-DD) (or press Enter to keep '" + existingClass.getDate() + "'): ");
            String newDate = scanner.nextLine();
            if (!newDate.isEmpty()) {
                LocalDate parsedDate = LocalDate.parse(newDate);
                existingClass.setDate(parsedDate);
            }

            System.out.print("New time (HH:MM) (or press Enter to keep '" + existingClass.getTime() + "'): ");
            String newTime = scanner.nextLine();
            if (!newTime.isEmpty()) {
                LocalTime parsedTime = LocalTime.parse(newTime);
                existingClass.setTime(parsedTime);
            }

            System.out.print("New location (or press Enter to keep '" + existingClass.getLocation() + "'): ");
            String newLocation = scanner.nextLine();
            if (!newLocation.isEmpty()) {
                existingClass.setLocation(location);
            }

            System.out.print("New equipment needed (or press Enter to keep '" + existingClass.getEquipment() + "'): ");
            String newEquipment = scanner.nextLine();
            if (!newEquipment.isEmpty()) {
                existingClass.setEquipment(equipment);
            }

            workoutService.updateWorkoutClass(existingClass);
            System.out.println("Workout class updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating workout class: " + e.getMessage());
        }
    }

    /**
    * Allows an admin to delete a workout class by entering its ID.
    * Confirms existence and prompts for deletion confirmation.
    *
    * @param scanner Scanner used to read admin input
    * @param workoutService Service used to fetch and delete class data
    */
    public void deleteWorkoutClass(Scanner scanner, WorkoutClassService workoutService) {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());

        try {
            WorkoutClass workoutClass = workoutService.getWorkoutClassById(classId);
            if (workoutClass == null) {
                System.out.println("❌ Class not found.");
                return;
            }

            System.out.print("Are you sure you want to delete this class? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                workoutService.deleteWorkoutClass(classId);
                System.out.println("✅ Class deleted successfully.");
            } else {
                System.out.println("❌ Class deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting class: " + e.getMessage());
        }
    }
}

